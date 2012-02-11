window.onload = function () {
    var Canvas_container = document.getElementById('canvas_container');
    var My_Button1 = document.getElementById('Button_1');

    var paper = new Raphael(Canvas_container, '100%', '100%');

    var startDraw = false;
    var vopacity = 0.3; //circels & curves
    var color = 'green';
    var line;
    var circle;
    var rect;
    var triangle;   //sometimes not triangle, actually may be used for describing any shape's
    var ln;                     //length between 2 points
    var startx, starty;
    var arrln = 300, i, j;    //array length
    var myArray = new Array(); // array for peaks
    var Width = document.getElementsByTagName("div")["canvas_container"].offsetWidth;
    var Height = document.getElementsByTagName("div")["canvas_container"].offsetHeight;
    var blockWidth = Width / arrln;
    var blockHeight = Height / arrln;
    var p = 2; //curve's speed //volatile! don't try to change it if you don't know what you are doing
    var level = 1.6;
    var line_s = '';

    var k = 0;  //debag

    //debag
    //for (i = 0; i < arrln; i++) {
    //   line = paper.path('M' + i * blockWidth + ',' + 0 + 'L' + i * blockWidth + ',' + Height);
    //   line = paper.path('M' + 0 + ',' + i * blockHeight + 'L' + Width + ',' + i * blockHeight);
    //}
    //debag

    for (i = 0; i < arrln; i++) {
        myArray[i] = new Array();
        for (j = 0; j < arrln; j++) {
            myArray[i][j] = 0;
        } //for[j]
    } //for [i]
    /**/


    Canvas_container.onclick = function (e) {
        e = e || window.event;

        if (!startDraw) {
            startDraw = true;
            startx = e.clientX;
            starty = e.clientY;
            circle = paper.circle(startx, e.clientY, 0);
            circle.attr({ fill: '#9cf', opacity: vopacity, stroke: 'none' });
        } //endif
        else {
            startDraw = false;
            circle = undefined;
            for (i = 0; i < arrln; i++) {
                for (j = 0; j < arrln; j++) {
                    var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth - startx) * (i * blockWidth - startx) + (j * blockHeight - starty) * (j * blockHeight - starty)), p))

                    if (some_math_operations) {
                        //if()
                        myArray[i][j] += (6 * (ln * ln) / some_math_operations - level);
                        //myArray[i][j] += 1000 * ln / some_math_operations;
                    }
                    else
                        myArray[i][j] += level * 10;

                } //for[j]
            } //for[i]

            //a = myArray[i][j]
            //b = myArray[i+1][j]
            //c = myArray[i+1][j+1]
            //d = myArray[i][j+1]

            //-----------debag------------
            //k++;
            //alert(k); //debug

            //if (5 == k) {
            // alert(line_s);
            //  line = paper.path(line_s);
            // } //endif           
            //line.attr({ fill: '#9cf' });
            //line = undefined;

            /**/
        //} //endelse (startDraw)
        //------------debag--------------
    } // end onclick function

    Canvas_container.onmousemove = function (e) {
        e = e || window.event;
        if (5 == k) {
            line = undefined;
        }

        if (startDraw) {
            ln = Math.sqrt((e.clientX - startx) * (e.clientX - startx) + (e.clientY - starty) * (e.clientY - starty));
            circle.animate({ r: ln }, 10);
        } //endif
    } // end onmousemove


    document.getElementById('Button_1').onclick = function () {
        for (i = 0; i < arrln; i++) {
            for (j = 0; j < arrln; j++) {
                if (!((myArray[i][j] < 0) && (myArray[i + 1][j] < 0) && (myArray[i][j + 1] < 0) && (myArray[i + 1][j + 1] < 0)))
                    if (((myArray[i][j] > 0) && (myArray[i + 1][j] > 0) && (myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0))) { //&& (!(myArray[i][j] > 10)) //debag
                        var j0 = j;
                        // var i0 = i;
                        while (((myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 2] > 0) && (myArray[i + 1][j + 2] > 0)) && (j < arrln)) j++;
                        rect = paper.rect(i * blockWidth, j0 * blockHeight, blockWidth, (j - j0 + 1) * blockHeight);
                        rect.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                    } else
                        if (((myArray[i][j] > 0) && (myArray[i][j + 1] > 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] < 0)) || ((myArray[i][j] < 0) && (myArray[i][j + 1] < 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] > 0))) {
                            line = undefined;
                            line_s += 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight;
                            if (myArray[i][j] > 0)
                                line = 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + i * blockWidth + ',' + j * blockHeight + 'L' + i * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight;
                            else
                                line = 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight;
                            triangle = paper.path(line);
                            triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                        } //endif 2+ 2- (left & right)
                        else
                            if (((myArray[i][j] > 0) && (myArray[i][j + 1] < 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] < 0)) || ((myArray[i][j] < 0) && (myArray[i][j + 1] > 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] > 0))) {
                                line = undefined;
                                line_s += 'M' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                if (myArray[i][j] > 0)
                                    line = 'M' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + i * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                else
                                    line = 'M' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + i * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                triangle = paper.path(line);
                                triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                            } //endif 2+ 2- (up & down)
                            else
                                if (((myArray[i][j] > 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] < 0) && (myArray[i][j + 1] < 0)) || ((myArray[i][j] < 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 1] > 0))) {
                                    line = undefined;
                                    line_s += 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight;
                                    if (myArray[i][j] > 0)
                                        line = 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + i * blockWidth + ',' + j * blockHeight + 'z';
                                    else
                                        line = 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + i * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + j * blockHeight + 'z';
                                    triangle = paper.path(line);
                                    triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                } //endif 1+ 3- (upperleft)
                                else
                                    if (((myArray[i][j] < 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] < 0) && (myArray[i][j + 1] < 0)) || ((myArray[i][j] > 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 1] > 0))) {
                                        line = undefined;
                                        line_s += 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                        if (myArray[i + 1][j] > 0)
                                            line = 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + j * blockHeight + 'z';
                                        else
                                            line = 'M' + (i + (myArray[i][j]) / (myArray[i][j] - myArray[i + 1][j])) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + i * blockWidth + ',' + (j + 1) * blockHeight + 'L' + i * blockWidth + ',' + j * blockHeight + 'z';
                                        triangle = paper.path(line);
                                        triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                    } //endif 1+ 3- (upperright)
                                    else
                                        if (((myArray[i][j] < 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] < 0) && (myArray[i][j + 1] > 0)) || ((myArray[i][j] > 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 1] < 0))) {
                                            line = undefined;
                                            line_s += 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight;
                                            if (myArray[i][j + 1] > 0)
                                                line = 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + i * blockWidth + ',' + (j + 1) * blockHeight + 'z';
                                            else
                                                line = 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight + 'L' + i * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + j * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + 1) * blockHeight + 'z';
                                            triangle = paper.path(line);
                                            triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                        } //endif 1+ 3- (downleft)
                                        else
                                            if (((myArray[i][j] < 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 1] < 0)) || ((myArray[i][j] > 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] < 0) && (myArray[i][j + 1] > 0))) {
                                                line = undefined;
                                                line_s += 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                                if (myArray[i + 1][j + 1] > 0)
                                                    line = 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + 1) * blockHeight + 'z';
                                                else
                                                    line = 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + j * blockHeight + 'L' + i * blockWidth + ',' + j * blockHeight + 'L' + i * blockWidth + ',' + (j + 1) * blockHeight + 'z';
                                                triangle = paper.path(line);
                                                triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                            } //endif 1+ 3- (downright)
                                            else
                                                if (((myArray[i][j] > 0) && (myArray[i + 1][j] < 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 1] < 0)) || ((myArray[i][j] < 0) && (myArray[i + 1][j] > 0) && (myArray[i + 1][j + 1] < 0) && (myArray[i][j + 1] > 0))) {
                                                    // line = paper.path('M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight);
                                                    //line = paper.path('M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i) * blockWidth + ',' + (j + (myArray[i][j]) / (myArray[i][j] - myArray[i][j + 1])) * blockHeight);
                                                    //line_s += 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                                    //line_s += 'M' + (i + (myArray[i][j + 1]) / (myArray[i][j + 1] - myArray[i + 1][j + 1])) * blockWidth + ',' + (j + 1) * blockHeight + 'L' + (i + 1) * blockWidth + ',' + (j + (myArray[i + 1][j]) / (myArray[i + 1][j] - myArray[i + 1][j + 1])) * blockHeight;
                                                    //line.attr({ fill: '#9cf' });
                                                } //endif 1+ 3- (downright)
                //////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!^!!!!!!!!!!!!!
            } //endif 4+ 4-
        } //for[j]
    } //for[i]     
}

}                                                                             //end onload function
/**/
