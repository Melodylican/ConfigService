(function($){
    function dialogClass(opt){
        this.opt = opt;
        this.$mask = null;
        this.$popup = null;
    };
    dialogClass.prototype.show = function(){
        var self = this;
        if(this.opt.mask){
            this.$mask = this.setMask();
            $("body").append(self.$mask);
            this.$mask.height($(document).height());
        }
        this.$popup = this.drawPopup();
        $("body").append(this.$popup);
        this.setPosition();
        $(window).resize(function(){
            self.setPosition();
        });
    };
    dialogClass.prototype.close = function(fn){
        if(this.$mask){
            this.$mask.remove();
        };
        this.$popup.remove();
        fn && fn();
    };
    dialogClass.prototype.drawPopup = function(){
        var self = this;
        var $popupHtml = $('<div class="popup_container" style="background:#000;background:rgba(0,0,0,0.6);padding:8px;border-radius:3px;position:absolute;top:0;left:0;z-index:'+self.opt.zIndex+';">' +
                            '<div class="pop-up">'+
                                '<div id="dialogContent" class="dialogContent">' +
                                     '<p class="loading">数据加载中...</p>'+
                                '</div>' +
                            '</div>'+
                          '</div>');
        if(this.opt.dialogClass){
            $popupHtml.addClass(this.opt.dialogClass);
        }
        var pos = ($.browser.msie && parseInt($.browser.version,10) <= 6 ) ? 'absolute' : 'fixed'; // IE6 Fix
        $popupHtml.css({
            position: pos,
            zIndex: self.opt.zIndex,
            margin: 0,
            width:self.opt.width + "px",
            height:self.opt.height + "px"
        });
        $popupHtml.find(".pop-up").css({
            "height":self.opt.height,
            "zoom":"1",
            "background":"#fff"
        });
        if(this.opt.title){
            var title = '<div style="height:40px;line-height:40px;padding:0 20px 0 30px;border-bottom:1px solid #d0d0d0;background:#ececec;">'+
                        '<span>'+self.opt.title+'</span>';
            title += '</div>';
            $popupHtml.find(".pop-up").prepend(title);
            $popupHtml.find("#close").click(function(){
                self.close();
            });
        }
        if(this.opt.closeButtom){
            $close = $('<a id="close" href="javascript:void(0)">关闭</a>');
            $close.click(function(){
                self.close();
            });
            $popupHtml.append($close);
        }
        if(this.opt.button){
            var $btnContent = $('<div class="submit"></div>');
            $.each(self.opt.button,function(id,v){
                $('<a id="'+id+'" class="'+v.className+'" style="margin:0 5px;">'+v.name+'</a>').appendTo($btnContent).click(function(){
                    v.fn && v.fn(self.$popup,self);
                   self.close();
                });
            });
            $btnContent.css({"position":"absolute","left":"0","bottom":"20px","width":"100%","text-align":"center"});
            $popupHtml.find(".pop-up").append($btnContent);
        }
        $popupHtml.find("#dialogContent").html(self.opt.content);
        return $popupHtml;
    };
    dialogClass.prototype.setMask = function(){
        var self = this;
        var $mask = $('<div><iframe style="position:absolute;width:100%;height:100%;filter:alpha(opacity=0);opacity=0;border-style:none;"></iframe></div>')
        $mask.css({
            position: 'absolute',
            zIndex: self.opt.zIndex-1,
            top: '0',
            left: '0',
            width: '100%',
            height: '100%',
            background: self.opt.maskColor,
            opacity: self.opt.maskOpacity
        });
        return $mask;
    };
    dialogClass.prototype.setPosition = function(){
        var top = ($(window).height()/2) - (this.$popup.outerHeight()/2);
        var left = ($(window).width()/2) - (this.$popup.outerWidth()/2);
        if( $.browser.msie && parseInt($.browser.version,10) <= 6 ) {top = top + $(window).scrollTop();}
        this.$popup.css({
            top: top + 'px',
            left: left + 'px'
        });
        this.$mask.css({height:$(document).height()});
    };
    $.dialogObj = {
        defaultOptions:{
            width:600,
            height:300,
            content:'',
            zIndex:1000,
            title:'',
            mask:true,
            dialogClass:'',
            maskColor:'#000',
            maskOpacity:'.15',
            closeButtom:true
        },
        getzIndex:function(){
            var num = $(".popup_container").size();
            return $.dialogObj.defaultOptions.zIndex + num*5;
        },
        dialog:function(opt){
            var options = $.extend({},$.dialogObj.defaultOptions,opt);
            options.zIndex = $.dialogObj.getzIndex();
            var obj = new dialogClass(options);
            obj.show();
            return obj;
        },
        popup:function(){
            var opt = {
                width:300,
                height:150,
                button:{'ok':{'name':'确定',"className":"btn"}},
                dialogClass:this.type
            };
            if(arguments[0]){
                opt.content = arguments[0];
            }
            switch(this.type){
                case "error": // continue
                case "prompt":
                case "warn":
                case "alert":
                    if(arguments[1]){
                        if(typeof arguments[1] == 'function'){
                            opt.button.ok.fn= arguments[1];
                        }else if(typeof arguments[1] == 'object'){
                             $.extend(opt, arguments[1]);
                        }
                    }
                    if(arguments[2] && typeof arguments[2] == 'object'){
                         $.extend(opt, arguments[2]);
                    }
                    break;
                case "confirm":
                    opt.button.cancel = {'name':'取消',"className":"cancel"};
                    if(arguments[1]){
                        if(typeof arguments[1] == 'function'){
                            opt.button.ok.fn= arguments[1];
                        }else if(typeof arguments[1] == 'object'){
                             $.extend(opt, arguments[1]);
                        }
                    }
                    if(arguments[2]){
                        if(typeof arguments[2] == 'function'){
                            opt.button.cancel.fn= arguments[2];
                        }else if(typeof arguments[2] == 'object'){
                             $.extend(opt, arguments[2]);
                        }
                    }
                    if(arguments[3] && typeof arguments[3] == 'object'){
                         $.extend(opt, arguments[3]);
                    }
                    break;
                case "mask":
                    opt.width = 300;
                    opt.height = 50;
                    opt.button = {};
                    break;
                default:
                    break;
            }
            var options = $.extend({},$.dialogObj.defaultOptions,opt);
            options.zIndex = $.dialogObj.getzIndex();
            var obj = new dialogClass(options);
            obj.show();
        }
    }
    jDialog = function(opt){
        return  $.dialogObj.dialog(opt);
    };
    jAlert = function(){
        this.type = "alert";
        $.dialogObj.popup.apply(this,arguments);//content,callback,option
    };
    jConfirm = function(){
        this.type = "confirm";
        $.dialogObj.popup.apply(this,arguments);//content,okCallback,cancelCallback,option
    };
    jError = function(){
        this.type = "error";
        $.dialogObj.popup.apply(this,arguments);//content,callback,option
    };
    jPrompt = function(){
        this.type = "prompt";
        $.dialogObj.popup.apply(this,arguments);//content,callback,option
    };
    jWarn = function(){
        this.type = "warn";
        $.dialogObj.popup.apply(this,arguments);//content,callback,option
    };
    setMask = function(){
        this.type = "mask";
        $.dialogObj.popup.apply(this,arguments);//content
    }
    removeMask = function(){
        $(".mask").prev("div").remove();
        $(".mask").remove();
    }
    info = function(text){
        var textInfo = $('<div class="textInfo">'+ text +'</div>');
        $("body").append(textInfo);
        var s = $(window).scrollTop();
        textInfo.animate({
            top:s+"px"
        });
        setTimeout(function(){
            textInfo.animate({
                top:"-30px"
            },function(){
                textInfo.remove();
            });
        },2000)
    }
})(jQuery);




(function($){
    function tipclass(ele,opt){
        this.opt = opt;
        this.ele =$(ele);
    };
    tipclass.prototype.init=function(){
       
    };
    tipclass.prototype.bindEven=function(){
       
    };
    $.fn.functionName= function(options){
        var options = $.extend({
           
        },options);
        return this.each(function(){
            var obj = new tipclass(this,options);
            obj.init();
        });
    };
})(jQuery);

