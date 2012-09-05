jQuery.fn.Hint = function (o, signX, signY) {
    o = jQuery.extend({
        trigger:"mouseover"
    }, o);
    function hint(e, event){
		var thus = $("<div class='hint_box'>"+$(e).attr("hint")+"</div>").appendTo("body");
		var width = $(".hint_box").width(), height = $(".hint_box").height();
		var left = (/*event.pageX*/$(e).offset().left + $(e).width() / 2 + (signX < 0 ? -12 : 8) - (signX < 0 ? width : 0));
		var top = (/*event.pageY*/$(e).offset().top + $(e).height() / 2 + (signY < 0 ? -12 : 8) - (signY < 0 ? height : 0));
		if(left < 0) {
			left = (/*event.pageX*/$(e).offset().left + $(e).width() / 2 - (signX < 0 ? -12 : 8));
		}
		if(top < 0) {
			top = (/*event.pageY*/$(e).offset().top + $(e).height() / 2 - (signY < 0 ? -12 : 8));
		}
		thus.attr('style','left:'+left+'px;top:'+top+'px;');
		thus.fadeIn(1000);
    };
    $(this).filter('[hint]').each(function(){
        /*if(o.trigger=="focus"){
            $(this).focus(function(){
                hint(this);
            }).blur(function(){
                $('.hint_box').remove();
            });
        }*/
        if(o.trigger=="mouseover"){
            $(this).mouseover(function(e){
                hint(this, e);
            }).mouseout(function(){
                $('.hint_box').remove();
            });
        }
    });
}

getStyleProperty = function getStyleProperty(cssName, className, propertyName) {
	var styles = document.styleSheets, i, l, IE = "\v"=="v";
	for(i = 0, l = styles.length; i < l; i++) {
		if(styles[i].href.indexOf(cssName) >= 0) {
			break;
		}
	}
	var classes = (IE ? styles[i].rules : styles[i].cssRules);
	for(i = 0, l = classes.length; i < l; i++) {
		if(classes[i].selectorText.indexOf(className) >= 0) {
			break;
		}
	}
	var properties = classes[i].style;
	for(i = 0, l = properties.length; i < l; i++) {
		if(properties[i].indexOf(propertyName) >= 0) {
			break;
		}
	}
	return window[properties + "." + propertyName];
}

