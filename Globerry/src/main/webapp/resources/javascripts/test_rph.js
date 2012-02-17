window.onload = function () {
    var Canvas_container = document.getElementById('canvas_container');
    var My_Button1 = document.getElementById('Button_1');
    var paper = new Raphael(Canvas_container, '100%', '100%');
    var startDraw = false;
    var vopacity = 0.3;             
    var color = 'green';
    var circleColor = '#9cf';
    var line;
    var circle;
    var circles = new Array();
    var rect;
    var triangle;                   
    var ln;                         
    var startx, starty;
    var arrln = 50, i, j;           
    var myArray = new Array();     
    var Width = document.getElementsByTagName("div")["canvas_container"].offsetWidth;
    var Height = document.getElementsByTagName("div")["canvas_container"].offsetHeight;
    var blockWidth = Width / arrln;
    var blockHeight = Height / arrln;
    var p = 2;                      
    var level = 1.6;
    var line_s = '';
    var begLevel = level;
    var k = 0;                  
    var size = 0;               
    if (Width < 1500) arrln = 40;
    if (Width < 1000) arrln = 30;
    if (Width < 700) arrln = 25;
    for (i = 0; i < arrln + 1; i++) {
        myArray[i] = new Array();
        for (j = 0; j < arrln + 1; j++) {
            myArray[i][j] = 0;
        } 
    }
    Canvas_container.onclick = function (e) {
        e = e || window.event;

        if (!startDraw) {
            startDraw = true;
            startx = e.clientX;
            starty = e.clientY;
            circle = paper.circle(startx, e.clientY, 0);
            circle.attr({ fill: circleColor, opacity: vopacity, stroke: 'none' });
        }
        else {
            startDraw = false;
            circle = undefined;
            circles[size] = new Array();
            circles[size][1] = startx;
            circles[size][2] = starty;
            circles[size][3] = ln;
            size++;
        }
        Canvas_container.onmousemove = function (e) {
            e = e || window.event;
            if (5 == k) {
                line = undefined;
            }

            if (startDraw) {
                ln = Math.sqrt((e.clientX - startx) * (e.clientX - startx) + (e.clientY - starty) * (e.clientY - starty));
                circle.animate({ r: ln }, 10);
            }
        }
        document.getElementById('Button_1').onclick = function () {
            var circle_counter;
            var my2Array = new Array();
            var i2, j2;
            var arrln2 = 10;
            var littleBlockWidth = blockWidth / arrln2;
            var littleBlockHeight = blockHeight / arrln2;

            for (i = 0; i < arrln + 1; i++) {
                for (j = 0; j < arrln + 1; j++) {
                    myArray[i][j] = 0;
                }
            }
            for (i = 0; i < arrln + 1; i++) {
                for (j = 0; j < arrln + 1; j++) {
                    for (circle_counter in circles) {
                        var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth - circles[circle_counter][1]) * (i * blockWidth - circles[circle_counter][1]) + (j * blockHeight - circles[circle_counter][2]) * (j * blockHeight - circles[circle_counter][2])), p))
                        if (some_math_operations) {
                            myArray[i][j] += (6 * (circles[circle_counter][3] * circles[circle_counter][3]) / some_math_operations - level);
                        }
                        else
                            myArray[i][j] += level * 10;
                    }
                }
            }


            for (i2 = 0; i2 < arrln2 + 1; i2++) {
                my2Array[i2] = new Array();
                for (j2 = 0; j2 < arrln2 + 1; j2++) {
                    my2Array[i2][j2] = 0;
                }
            }

            paper.clear();
            for (i in circles) {
                circle = paper.circle(circles[i][1], circles[i][2], circles[i][3]);
                circle.attr({ fill: circleColor, opacity: vopacity, stroke: 'none' });
            }
            for (i = 0; i < arrln; i++) {
                for (j = 0; j < arrln; j++) {
                    if (!((myArray[i][j] < 0) && (myArray[i + 1][j] < 0) && (myArray[i][j + 1] < 0) && (myArray[i + 1][j + 1] < 0))) {
                        if (((myArray[i][j] > 0) && (myArray[i + 1][j] > 0) && (myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0))) {
                            var j0 = j;
                            while (((myArray[i][j + 1] > 0) && (myArray[i + 1][j + 1] > 0) && (myArray[i][j + 2] > 0) && (myArray[i + 1][j + 2] > 0)) && (j < arrln)) j++;
                            rect = paper.rect(i * blockWidth, j0 * blockHeight, blockWidth, (j - j0 + 1) * blockHeight);
                            rect.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                            j++;
                        }
                        for (i2 = 0; i2 < arrln2 + 1; i2++) {
                            for (j2 = 0; j2 < arrln2 + 1; j2++) {
                                my2Array[i2][j2] = 0;
                            }
                        }
                        for (i2 = 0; i2 < arrln2 + 1; i2++) {
                            for (j2 = 0; j2 < arrln2 + 1; j2++) {
                                for (circle_counter in circles) {
                                    var some_math_operations = (Math.pow(Math.sqrt((i * blockWidth + i2 * littleBlockWidth - circles[circle_counter][1]) * (i * blockWidth + i2 * littleBlockWidth - circles[circle_counter][1]) + (j * blockHeight + j2 * littleBlockHeight - circles[circle_counter][2]) * (j * blockHeight + j2 * littleBlockHeight - circles[circle_counter][2])), p))
                                    if (some_math_operations) {
                                        my2Array[i2][j2] += (6 * (circles[circle_counter][3] * circles[circle_counter][3]) / some_math_operations - level);
                                    }
                                    else
                                        my2Array[i2][j2] += level * 10;
                                }
                            }
                        }
                        for (i2 = 0; i2 < arrln2; i2++) {
                            for (j2 = 0; j2 < arrln2; j2++) {
                                if ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2 + 1] > 0)) {
                                    var j0 = j2;
                                    while (((my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 2] > 0) && (my2Array[i2 + 1][j2 + 2] > 0)) && (j2 < arrln2)) j2++;
                                    rect = paper.rect(i * blockWidth + i2 * littleBlockWidth, j0 * littleBlockHeight + j * blockHeight, littleBlockWidth, (j2 - j0 + 1) * littleBlockHeight);
                                    rect.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                    j2++;
                                }
                                if (((my2Array[i2][j2] > 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2][j2 + 1] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0))) {
                                    line = undefined;
                                    if (my2Array[i2][j2] > 0)
                                        line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight;
                                    else
                                        line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight;
                                    triangle = paper.path(line);
                                    triangle.translate(i * blockWidth, j * blockHeight);
                                    triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                }
                                else
                                    if (((my2Array[i2][j2] > 0) && (my2Array[i2][j2 + 1] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2][j2 + 1] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0))) {
                                        line = undefined;
                                        if (my2Array[i2][j2] > 0)
                                            line = 'M' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight;
                                        else
                                            line = 'M' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight;
                                        triangle = paper.path(line);
                                        triangle.translate(i * blockWidth, j * blockHeight);
                                        triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                    }
                                    else
                                        if (((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] > 0))) {
                                            line = undefined;
                                            if (my2Array[i2][j2] > 0)
                                                line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                            else
                                                line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                            triangle = paper.path(line);
                                            triangle.translate(i * blockWidth, j * blockHeight);
                                            triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                        }
                                        else
                                            if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] > 0))) {
                                                line = undefined;
                                                if (my2Array[i2 + 1][j2] > 0)
                                                    line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                                else
                                                    line = 'M' + (i2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2 + 1][j2])) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'z';
                                                triangle = paper.path(line);
                                                triangle.translate(i * blockWidth, j * blockHeight);
                                                triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                            }
                                            else
                                                if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0))) {
                                                    line = undefined;
                                                    if (my2Array[i2][j2 + 1] > 0)
                                                        line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                    else
                                                        line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2) * littleBlockWidth + ',' + (j2 + (my2Array[i2][j2]) / (my2Array[i2][j2] - my2Array[i2][j2 + 1])) * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                    triangle = paper.path(line);
                                                    triangle.translate(i * blockWidth, j * blockHeight);
                                                    triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                                }
                                                else
                                                    if (((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0))) {
                                                        line = undefined;
                                                        if (my2Array[i2 + 1][j2 + 1] > 0)
                                                            line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                        else
                                                            line = 'M' + (i2 + (my2Array[i2][j2 + 1]) / (my2Array[i2][j2 + 1] - my2Array[i2 + 1][j2 + 1])) * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + (j2 + (my2Array[i2 + 1][j2]) / (my2Array[i2 + 1][j2] - my2Array[i2 + 1][j2 + 1])) * littleBlockHeight + 'L' + (i2 + 1) * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + j2 * littleBlockHeight + 'L' + i2 * littleBlockWidth + ',' + (j2 + 1) * littleBlockHeight + 'z';
                                                        triangle = paper.path(line);
                                                        triangle.translate(i * blockWidth, j * blockHeight);
                                                        triangle.attr({ fill: color, stroke: color, opacity: vopacity, 'stroke-opacity': vopacity / (3.3) });
                                                    }
                                                    else
                                                        if (((my2Array[i2][j2] > 0) && (my2Array[i2 + 1][j2] < 0) && (my2Array[i2 + 1][j2 + 1] > 0) && (my2Array[i2][j2 + 1] < 0)) || ((my2Array[i2][j2] < 0) && (my2Array[i2 + 1][j2] > 0) && (my2Array[i2 + 1][j2 + 1] < 0) && (my2Array[i2][j2 + 1] > 0))) {
                                                        }

                            }
                        }
                    }
                }
            }
        }
        level = begLevel * Math.pow(0.9, size);
    }

}
