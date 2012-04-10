window.onload = function () {
   /* $("div").click(function (event) {
        event = event || window.event;
        var t = event.target || event.srcElement
        alert(t.id)
    }); */
    var headerflag = 0
    $("#SliderRequest").hide();
    $("#upperToggle").hide();
    $("#bottomToggle").hide();

    $("#buttonHeadSwitcher").click(function (event) {
        if ((headerflag % 2) == 0)
            $("#head").slideToggle("slow", function () {
                $("#SliderRequest").slideToggle("slow");
                $("#upperToggle").slideToggle("slow");
                $("#bottomToggle").slideToggle("slow");
            });
        else
            $("#SliderRequest").slideToggle("slow", function () { $("#head").slideToggle("slow") });

        headerflag++;

    });
    $("#bottomToggle").click(function (event) {
        if ((headerflag % 2) == 0)
            $("#head").slideToggle("slow", function () { $("#SliderRequest").slideToggle("slow") });
        else {
            $("#bottomToggle").slideToggle("slow");
            $("#upperToggle").slideToggle("slow");
            $("#SliderRequest").slideToggle("slow", function () { $("#head").slideToggle("slow") });
        }
        headerflag++;
    });
    

    var prev = document.getElementById('jan');
    $(".calMonth").click(function (event) {
        prev.style.background = 'rgb(37, 46, 64)';
        prev = this;
        this.style.background = 'rgb(236, 72, 7)';
    }); 
    
    //init();

}
