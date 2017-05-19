(function($) {
    $.fn.tabs =    function() {
        var    opts = {
            switchingMode: "mouseover"  // or "click"
        };
        var obj = $(this);
        var clickIndex = 0;
        obj.addClass("tabsDiv");
        $("ul li:first", obj).addClass("tabsSeletedLi");
        $("ul li", obj).not(":first").addClass("tabsUnSeletedLi");
        $("div", obj).not(":first").hide();
        $("ul li", obj).bind(opts.switchingMode,
        function() {
            if (clickIndex != $("ul li", obj).index($(this))) {
                clickIndex = $("ul li", obj).index($(this));
                $(".tabsSeletedLi", obj).removeClass("tabsSeletedLi").addClass("tabsUnSeletedLi");
                $(this).removeClass("tabsUnSeletedLi").addClass("tabsSeletedLi");
                var    divid = $("a", $(this)).attr("href").substr(1);
                $("div", obj).hide();
                $("#" + divid, obj).show();
            };
        });
    };
})(jQuery);