/* =============================================================================
 * sparkplug.js v1.1.0
 * ============================================================================= */

// setup
;(function (global, $){
  'use strict';

  $(function () {
    // placeholder attribute polyfill for IE8-9
    if ($.fn.placeholder) {
      $('input[placeholder], textarea[placeholder]').placeholder();
    }

    // autofocus attribute polyfill for IE8-9
    var $focusEl = $('[autofocus]:not(:focus)').eq(0);
    if ($focusEl.length) {
      $focusEl.focus();
    }
  });
}(window, jQuery));

// TODO: Modularization
;(function (global, $) {
  'use strict';

  $(function (){
    // 開閉可能な标题ボックス
    $('.titledbox.titledbox-collapsible .titledbox-header').on('click', function () {
      var $header = $(this);
      var $body = $header.next('.titledbox-body');
      var $container = $header.parent();

      if (!$body.length) {
        return;
      }

      // 标题ボックスのbodyトグルする
      $body.slideToggle('fast', function () {
        $container.toggleClass(function () {
          if (!$container.hasClass('collapsed')) {
            $header.find('.icon-caret-down').removeClass('icon-caret-down').addClass('icon-caret-right');
          } else {
            $header.find('.icon-caret-right').removeClass('icon-caret-right').addClass('icon-caret-down');
          }
          return 'collapsed';
        });
      });
    });

    // subnavの上部固定用。subnavが存在しない場合はスキップ。
    var $nav = $('#subnav');
    if ($nav.size() > 0) {
      //navの位置
      var navTop = $nav.offset().top;
      //スクロールするたびに実行
      $(global).scroll(function () {
        var currentTop = $(this).scrollTop();
        //スクロール位置がnavの位置より下だったらクラスsubnav-fixed追加（ヘッダーの分の41px追加）
        if ((currentTop + 41) >= navTop) {
          $nav.addClass('subnav-fixed');
        } else if ((currentTop - 41) <= navTop) {
          $nav.removeClass('subnav-fixed');
        }
      });
    }

    // 画面下端にはりつく「ページの先頭へ」ボタン
    var $topBtn = $('#btn-pagetop');
    $topBtn.hide();
    $(global).scroll(function () {
      if ($(this).scrollTop() > 80) {
        $topBtn.fadeIn('fast');
      } else {
        $topBtn.fadeOut('fast');
      }
    });
    //スクロールしてトップ
    $topBtn.click(function () {
      $('body,html').animate({ scrollTop: 0 }, 200);
      return false;
    });
  });
}(window, jQuery));

// Datagrid
;(function (global, $) {
  'use strict';

  var Datagrid = function (el, options) {
    this.initialize.apply(this, arguments);
  };

  Datagrid.prototype = {
    initialize: function (el, options) {
      this.options = $.extend({}, $.fn.spDatagrid.defaults, options);
      var $el = this.$el = $(el);
      // theadに配置された親チェックボックス
      this.$parent = $el.find('.table thead th.row-selector input[type=checkbox]');
      // tbodyの各行に配置された子チェックボックス
      this.$children = $el.find('table tbody td.row-selector input[type=checkbox]');
    },
    childrenToggle: function () { // 全ての子チェックボックストグル
      this.$children.prop('checked', this.$parent.prop('checked'));
      this.highlightToggles();
    },
    highlightToggle: function (child) { // 選択行のハイライトトグル
      if (this.options.highlight) {
        $(child).closest('tr').toggleClass('row-selected', $(child).prop('checked'));
      }
    },
    highlightToggles: function () { // 全ての行(ヘッダ除く)のハイライトトグル
      var that = this;
      this.$children.each(function () {
        that.highlightToggle(this);
      });
    },
    parentIntegrity: function (child) { // 親チェックボックスの整合性とる
      var $child = $(child);
      if (!$child.prop('checked')) {
        this.$parent.prop('checked', false);
        return;
      }
      var that = this;
      this.$parent.prop('checked', true);
      // 子チェックボックスが全てチェックされているか走査し、一つでもチェックが無ければ親チェックボックスのチェック外す
      this.$children.each(function () {
        if (!$(this).prop('checked')) {
          that.$parent.prop('checked', false);
        }
      });
    }
  };

  // jQueryのプラグインとして定義
  $.fn.spDatagrid = function (options) {
    // true or falseの指定による機能の有効、無効の切り替えも可能とする
    var ops = typeof options === 'object' ? options : {};
    var enabled = typeof options === 'boolean' ? options : true;

    this.each(function () { // 指定したオプションで複数のデータグリッドに対して処理
      var $this = $(this);
      var instance = new Datagrid(this, ops);

      // 複数回呼ばれた時のメモリリーク、意図しない挙動の回避のため、イベントリスナ清除
      $this.off('click.sp.datagrid');

      if (!enabled) { // 機能無効化
        // ハイライト除去
        instance.$children.closest('tr').removeClass('row-selected');
        return;
      }

      // インスタンス生成時のデータグリッドのチェックボックスにチェックがあるケースに対応
      instance.$children.each(function () {
        instance.parentIntegrity(this);
      });
      instance.highlightToggles();

      // データグリッドまで伝搬したクリックイベントで一元的に制御
      $this.on('click.sp.datagrid', function (e) {
        var src = e.target;

        if (src === instance.$parent[0]) { // 親チェックボックスクリックした時
          instance.childrenToggle();
        } else if (src === instance.$parent.closest('th')[0]) { // 親チェックボックスのセルクリックした時
          instance.$parent.prop('checked', !instance.$parent.prop('checked'));
          instance.childrenToggle();
        }

        instance.$children.each(function () {
          if (src === this) { // 子チェックボックスクリックした時
            instance.parentIntegrity(this);
            instance.highlightToggle(this);
            return false;
          } else if (src === $(this).closest('td')[0]) { // 子チェックボックスのセルクリックした時
            $(this).prop('checked', !$(this).prop('checked'));
            instance.parentIntegrity(this);
            instance.highlightToggle(this);
            return false;
          }
        });
      });
      return this;
    });
  };

  // オプションのデフォルト値
  $.fn.spDatagrid.defaults = {
    highlight: true // 選択された行ハイライトするかどうか
  };

  // data-api
  $(function () {
    var $el = $('.datagrid[data-sp-toggle=datagrid]');
    if ($el.length) {
      $el.each(function () { // データ属性に指定したオプションで個別にプラグイン実行
        var data = $(this).data();
        var ops = {};

        if (typeof data.spHighlight === 'boolean') { // データ属性からオプション値取得
          ops.highlight = data.spHighlight;
        }
        $(this).spDatagrid(ops);
      });
    }
  });
}(window, jQuery));