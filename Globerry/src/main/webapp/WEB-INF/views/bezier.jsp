<%-- 
    Document   : bezier
    Created on : 21.06.2012, 11:37:08
    Author     : Ed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script type="text/javascript" src="resources/javascripts/jquery.ui-slider.js"></script>
        <link type="text/css" href="resources/lib/jquery-ui-1.8.21/css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet" />
        <title>JSP Page</title>
        <style>
            .lines {
                stroke: black;
                stroke-width: 1;
            }
            .axis {
                stroke: black;
                stroke-width: 1;
            }
            .connect {
                fill:#888888; stroke:none
            }
            .positive {
                fill:#ff0000; stroke:none
            }
            .negative {
                fill: #0000ff; stroke:none
            }
        </style>
    </head>
    <body>
        <table>
            <tr>
                <td><div id="wolfram"></div></td>
            </tr>
            <tr>
                <td>
                    <div id="z">
                        Z=<input id="minVal" type="text" value="2.7" size="4"/> step:<input id="step" readonly="true" size ="4"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td><input type="checkbox" id="alert"/></td>
            </tr>
            <tr>
                <td><button onclick="redraw()">Перерисовать</button></td>
            </tr>
            <tr>
                <td>
                    <div id="slider"/>

                </td>
            </tr>
        </table>
        <div id="pic"></div>




    </body>
    <script>
        
        var graph, points;
        $(document).ready($('#slider').slider({
            min : 0.5,
            max : 15,
            step : 0.01,
            value : 7.79,
            slide : function(event, ui) {
                $('#minVal').val(ui.value);
                redraw();
            }
        }
    ))
       
        function redraw() {
            var value = parseFloat($('#minVal').val());
            if(!value) {
                alert("Ты кто такой? Давай, до свидания!");
                return;
            }
            $('.curve').remove();
            //graph.drawIsoline(points, value);
            graph.drawSpans(points, value)
        }
       
        var NS = "http://www.w3.org/2000/svg";
        
       
        function Point(x, y) {;
            this.x = x;
            this.y = y;
        }
        
        function createElement(name) {
            return document.createElementNS(NS,name);
        }
        function foo(x) {
            return Math.sin(x) * Math.sin(x) + Math.cos(x) + 1/2*Math.sin(x*1.2) + 1/2*Math.cos(x*1.3)*Math.cos(x)*Math.cos(x);
            //return Math.sin(1/2*x);
        }
        function plotString(points) {
            var ret = "plot(";
            for(var i = 0, l = points.length; i < l; i++) {
                ret += points[i].weight + "/sqrt((x-" + points[i].x + ")^2 + (y - " + points[i].y + ")^2)";
                if(i != l-1) ret += "+";
            }
            ret += ")";
            return ret;
        }
        
        $(document).ready(function() {
            graph = Graph(1000,1000,-10,10,-8,5,50);
            $('#pic').append(graph.svg);
            //graph.draw(foo, 0.1);
            //graph.drawFunc(foo, 2);
            var point;
            points = [];
            /*point = Point(0, 0);
            point.weight = 1;
            points.push(point);
            
            point = Point(1, 1.5);
            point.weight = 1;
            points.push(point);
            
            point = Point(2, 0);
            point.weight = 2;
            points.push(point);*/
            point = Point(0, 0);
            point.weight = 3;
            points.push(point);
            
            
            point = Point(0, -2);
            point.weight = 1;
            points.push(point);
            
            point = Point(0, 1.5);
            point.weight = 1;
            points.push(point);
            
            point = Point(-3, 3);
            point.weight = 2;
            points.push(point);
            
            point = Point(-6, 0);
            point.weight = 1;
            points.push(point);
            
            point = Point(-6, -2);
            point.weight = 1;
            points.push(point);
            
            
            point = Point(3, 3);
            point.weight = 1;
            points.push(point);
            
            point = Point(5, 0);
            point.weight = 1;
            points.push(point);
            
            point = Point(5, -2);
            point.weight = 1;
            points.push(point);
            
            point = Point(5, -4);
            point.weight = 1;
            points.push(point);
            
            point = Point(0, -4.5);
            point.weight = 2;
            points.push(point);
            
            point = Point(8, -6);
            point.weight = 1.5;
            points.push(point);
            
            point = Point(3, -0.85);
            point.weight = 1.5;
            points.push(point);
            
            point = Point(9, 1.5);
            point.weight = 4;
            points.push(point);
            
            point = Point(-8, -4.75);
            point.weight = 4;
            points.push(point);
            
            //graph.drawRays(rays);
            graph.drawSpans(points, 6.88);
            //graph.drawSpans(points,3.35);
            
            $('#wolfram').append(plotString(points));
        });
        
        function Graph(w, h, _minX, _maxX, _minY, _maxY, step) {
            var width = w, height = h, minX = _minX, maxX = _maxX, maxY = _maxY, minY = _minY;
            var svg = createElement('svg');
            svg.appendChild(createAxis(step));
            var defs = createElement('defs');
            svg.appendChild(defs);
            var marker = createElement('marker');
            function createAxis(step) {
                var textOffset = 10;
                var g = createElement('g');
                var linesPath = ""
                var text = createElement('text');
                var tspan;
                var axisPath = "M 0 0 h " + w + "M 0 0 v " + h + "M 0 " + h + " h "+ w + "M " + w + " 0" + "v " + h;
                
                for(var i = 0; i <= width/step; i++) {
                    linesPath += "M" + i*step + " 0v" +height; 
                    tspan = createElement('tspan');
                    tspan.setAttribute('x', i*step);
                    tspan.setAttribute('y', height + textOffset*3);
                    tspan.textContent = i*step;
                    text.appendChild(tspan);
                    
                    tspan = createElement('tspan');
                    tspan.setAttribute('x', i*step);
                    tspan.setAttribute('y', height + textOffset);
                    var val = step*i*(maxX - minX)/width + minX;
                    tspan.textContent = val.toFixed(2);
                    text.appendChild(tspan);
                }
                
                for(var i = 1; i <= height/step; i++) {
                    linesPath += "M0 "+i*step + "h" + width;
                    tspan = createElement('tspan');
                    tspan.setAttribute('x', width + textOffset*5);
                    tspan.setAttribute('y', i*step);
                    tspan.textContent = i*step;
                    text.appendChild(tspan);
                    
                    tspan = createElement('tspan');
                    tspan.setAttribute('x', width + textOffset);
                    tspan.setAttribute('y', i*step);
                    
                    tspan.textContent = (i*step*(maxY - minY)/height + minY).toFixed(2);
                    text.appendChild(tspan);
                }
                
                var lines = createElement('path');
                lines.setAttribute('class', 'lines');
                lines.setAttribute('d', linesPath);
            
                var axis = createElement('path');
                axis.setAttribute('class', 'axis');
                axis.setAttribute('d', axisPath);
            
            
                g.appendChild(lines);
                g.appendChild(axis);
                text.setAttribute('font-size', '10px');
                text.setAttribute('font-weight', 'lighter');
                g.appendChild(text);
                return g;
            }
            function projectX(x) {
                return parseInt(width*(x - minX)/(maxX - minX));
            }
            function projectY(y) {
                return parseInt(height*(y - minY)/(maxY - minY));
            }
            function projectPoint(p) {
                return Point(projectX(p.x), projectY(p.y));
            }
            function projectXY(x, y){
                return Point(parseInt(width*(x - minX)/(maxX - minX)), parseInt(height*(y - minY)/(maxY - minY)));
            }
            function draw(func, step) {
                
                var rx = projectX(minX), ry = projectY(func(minX));
                var path = "M" + rx + " " + ry;
                for(var x = minX; x<=maxX; x += step) {
                    y = func(x);
                    rx = projectX(x);
                    ry = projectY(y);
                    path += "L" + rx + " " + ry;
                }
                var gr = createElement('path');
                gr.setAttribute('d', path);
                gr.setAttribute('fill', 'none');
                gr.setAttribute('stroke', 'green');
                gr.setAttribute('stroke-width',1);
                svg.appendChild(gr);   
            }
            function drawPoints(points) {
                if(points.length <= 1)
                    return;
                var x = points[0].x,y = points[0].y;
                var path = "M" + projectX(x) + " " + projectY(y);
                for(var i = 0, l = points.length; i < l; i++) {
                    path += "L " + projectX(points[i].x) + " " + projectY(points[i].y);
                }
                path += "z";
                appendPath(path, 'red', '4');
            }
            function appendPath(path, color, width, clazz) {
                var gr = createElement('path');
                gr.setAttribute('d', path);
                gr.setAttribute('fill', 'none');
                gr.setAttribute('stroke', color || 'green');
                gr.setAttribute('stroke-width',width || '2');
                if(clazz) {
                    gr.setAttribute('class', clazz);
                }
                svg.appendChild(gr);
            }
            function drawFunc(func, step) {
                var rays = [];
                for(var x = minX; x <= maxX ; x+= step) {
                    var d = x + 0.05*step;
                    rays.push(Ray(Point(x, func(x)),Point(d, func(d))));
                }
                drawRays(rays);
            }
            function drawRays(arr/*Массив лучей, начало луча - точка функции, направление - касетельная в этой точке*/, flag/*Рисовать опорные точки*/) {
                if(arr.length <= 1)
                    return;
                var ray1 = arr[0], ray2, c1, c2, factor = 0.3, prevT = null, factor2 = 0.6;
                var path = "M " + projectX(ray1.start.x) + " " + projectY(ray1.start.y), pathVect = "";
                var t1, t2;
                arr.push(arr[0]);
                for(var i = 1, l = arr.length; i < l; i++) {
                    ray2 = arr[i];
                    t1 = ray1.cross(ray2);
                    t2 = ray2.cross(ray1);
                    if(prevT == null) {
                        if(t1 >= 0 && t2 <= 0) {
                            c1 = ray1.getPoint(t1);
                            c2 = c1;
                            prevT = -t2;
                        }
                    }
                       
                    if(t1 >= 0 && t2 <= 0.01) {
                        c1 = ray1.getPoint(prevT);
                        c2 = ray2.getPoint(t2 * factor2);
                        prevT = -t2 * factor2;
                    } else if(t1 >= 0 && t2 >= 0.01) {
                        c1 = ray1.getPoint(prevT);
                        c2 = ray2.getPoint(-factor);
                        prevT = factor;
                    } else if(t1 < 0 && t2 < 0) {
                        c1 = ray1.getPoint(prevT);
                        c2 = ray2.getPoint(-factor/2);
                        prevT = factor/2;
                    } else if(t1 < 0 && t2 >0) {
                        c1 = ray1.getPoint(prevT);
                        c2 = ray2.getPoint(-factor);
                        prevT = factor;
                    }
                    
                    pathVect += "M " + projectX(ray1.start.x) + " " + projectY(ray1.start.y) + "L " + 
                        projectX(c1.x) + " " + projectY(c1.y) +
                        "L " + projectX(c2.x) + " " + projectY(c2.y) + "L " + projectX(ray2.start.x) + " " + projectY(ray2.start.y);
                    /*path += "M " + projectX(ray1.start.x) + " " + projectY(ray1.start.y) + "C " + projectX(c1.x) + " " + projectY(c1.y) + " "
                            + projectX(c2.x) + " " + projectY(c2.y) + " "
                            + projectX(ray2.start.x) + " " + projectY(ray2.start.y);*/
                    path += "C " + projectX(c1.x) + " " + projectY(c1.y) + " "
                        + projectX(c2.x) + " " + projectY(c2.y) + " "
                        + projectX(ray2.start.x) + " " + projectY(ray2.start.y);
                    ray1 = ray2;
                    
                    //appendCircle(c1);
                    //appendCircle(c2);
                    //appendCircle(ray1.start);
                }
                
                path += ' z';
                var gr = createElement('path');
                gr.setAttribute('d', path);
                gr.setAttribute('fill', 'none');
                gr.setAttribute('stroke', 'red');
                gr.setAttribute('stroke-width',4);
                gr.setAttribute('class', 'curve');
                svg.appendChild(gr);
                if(flag) {
                    gr = createElement('path');
                    gr.setAttribute('d', pathVect);
                    gr.setAttribute('fill', 'none');
                    gr.setAttribute('stroke', 'black');
                    gr.setAttribute('stroke-width',1);
                    gr.setAttribute('class', 'curve');
                }
                
                
                svg.appendChild(gr);
            
            }
            function appendCircle(point, r, clazz) {
                var circle = createElement('circle');
                circle.setAttribute('class', 'connect');
                circle.setAttribute('cx', projectX(point.x));
                circle.setAttribute('cy', projectY(point.y));
                circle.setAttribute('r', r || '5');
                if(clazz)
                    circle.setAttribute('class', clazz);
                svg.appendChild(circle);
            }
            function appendCircle2(x, y, r, clazz) {
                var circle = createElement('circle');
                circle.setAttribute('class', 'connect');
                circle.setAttribute('cx', projectX(x));
                circle.setAttribute('cy', projectY(y));
                circle.setAttribute('r', r || '5');
                if(clazz)
                    circle.setAttribute('class', clazz);
                svg.appendChild(circle);
            }
            function appendText(x, y, text, clazz) {
                var t = createElement('text');
                var tspan = createElement('tspan');
                tspan.setAttribute('x', projectX(x));
                tspan.setAttribute('y', projectY(y));
                tspan.textContent = text;
                t.appendChild(tspan);
                if(clazz)
                    t.setAttribute('class', clazz);
                svg.appendChild(t);
            }
            
            function drawSpans(point, zLevel) {
                function Z(x, y) {
                    var val = 0;
                    for(var i = 0, l = points.length; i < l; i++) {
                        var point = points[i];
                        val += point.weight/(Math.sqrt((point.x - x)*(point.x - x) + (point.y - y)*(point.y - y)));
                    }
                    return val;
                }
                function dZdxy(x, y) {
                    var dx = 0, dy = 0;
                    for(var i = 0, l = points.length; i < l; i++) {
                        var point = points[i];
                        var a = point.x, b = point.y;
                        //Нужно чтобы не вычилять три раза корень из (x-x_i)^2+(y-y_i)^2 при возведении в куб
                        var bigSqrt = Math.sqrt((x - a)*(x - a) + (y - b)*(y - b))
                        var znamenatel = bigSqrt * bigSqrt * bigSqrt / point.weight;
                        dx += (a - x)/znamenatel;
                        dy += (b - y)/znamenatel;
                    }
                    return Point(dx, dy);
                }
                function findBorder(_x, _y, direction, stepCount, positionFlag) {
                    var x = _x, y = _y, z = Z(x, y);
                    var count = 0;
                    var sign = z - zLevel;
                    var xPrev = x, zPrev = z, flag = false;
                    if(!stepCount)
                        stepCount = 1000;
                    if(stepCount < 1)
                        return null;
                    while(true) {
                        x = xPrev + direction*stepX;
                        z = Z(x, y);
                        count++;
                        if(sign*(z - zLevel) < 0)
                            break;
                        if(count > stepCount) {
                            flag = true;
                            break;
                        }
                        xPrev = x;
                        zPrev = z;
                    }
                    if(flag)
                        return null;
                    var x0;
                    if(typeof(positionFlag) == 'undefined') {
                        x0 = resolve(x, xPrev, z, zPrev, y, zLevel, eps, Z, 50);
                    } else {
                        x0 = resolve(x, xPrev, z, zPrev, y, zLevel, eps, Z, 50, positionFlag);
                    }
                    return x0;
                }
                function findSpans(span) {
                    var parent = span.getParent();
                    
                    var dir = -1, ret = [];
                    var parentLevel = parent ? parent.getLevel() : null, flag = false;
                    
                    for(var i = 0; i < 2; i++) {
                        if(span.getLevel() == 2)
                            console.log('log');
                        dir *= -1;
                        var level = span.getLevel() + dir;
                        var x, y = firstLevel + level*stepY, zCurrent, left, right;
                        if(level != parentLevel) {
                            x = span.getLeft().x;
                            zCurrent = Z(x, y);
                            if(zCurrent > zLevel) {
                                
                                x = findBorder(x, y, -1, false, false);
                            } else {
                                x = findBorder(x, y, 1, (span.getRight().x - x)/stepX, false);
                                if(!x) {
                                    if(dir == -1) {
                                        var l = span.getLeft(), r = span.getRight();
                                        r.next = l;
                                        l.previous = r;
                                    } else if(dir == 1) {
                                        var l = span.getLeft(), r = span.getRight();
                                        l.next = r;
                                        r.previous = l;
                                    }
                                    continue;
                                }
                            }
                            left = x;
                            var p1 = span.getLeft(), newSpan, flag = false;
                            var stepCount = span.getRight().x - span.getLeft().x > 4*stepX ? false : 8;
                            while(left) {
                                right = findBorder(left, y, 1, stepCount, false);
                                if(flag && left - ret[ret.length - 1].getRight().x < stepX) {
                                    ret[ret.length - 1].getRight().x = right;
                                    left = findBorder(right, y, 1,(span.getRight().x - right)/stepX - 2, false);
                                    continue;
                                }
                                appendCircle2(left, y, 3, 'curve');
                                appendCircle2(right, y, 3, 'curve');
                                
                                    
                                newSpan = Span(Node(left, y), Node(right, y), level);
                                newSpan.setParent(span);
                                if(dir == 1) {
                                    p1.next = newSpan.getLeft();
                                    newSpan.getLeft().previous = p1;
                                } else {
                                    p1.previous = newSpan.getLeft();
                                    newSpan.getLeft().next = p1;
                                }
                                p1 = newSpan.getRight();
                                flag = true;
                                ret.push(newSpan);
                                
                                left = findBorder(right, y, 1,(span.getRight().x - right)/stepX - 2, false);
                            }
                            if(dir == 1) {
                                newSpan.getRight().next = span.getRight();
                                span.getRight().previous = newSpan.getRight();
                                
                            } else {
                                newSpan.getRight().previous = span.getRight();
                                span.getRight().next = newSpan.getRight();
                            }
                            
                        } else if(parent) {
                            var parentLeft = parent.getLeft().x
                            if(span.getLeft().x < parentLeft - stepX) {
                                left = span.getLeft().x;
                                zCurrent = Z(left, y);
                                if(zCurrent > zLevel) {
                                    left = findBorder(left, y, -1, false, false);
                                } else {
                                    left = findBorder(left, y, 1, (parentLeft - left)/stepX - 1, false);
                                }
                                if(left && Math.abs(span.getParent().getLeft().x - left) > stepX) {
                                    var p1 = span.getLeft(), flag = false;
                                    while(left) {
                                        right = findBorder(left, y, 1, (parentLeft - left)/stepX - 1, false);
                                        if(!right) {
                                            right = parentLeft - stepX/2;
                                            flag = true;
                                        }
                                        newSpan = Span(Node(left,y), Node(right,y), level);
                                        newSpan.setParent(span);
                                        if(dir == 1) {
                                            p1.next = newSpan.getLeft();
                                            newSpan.getLeft().previous = p1;
                                        } else {
                                            newSpan.getLeft().next = p1;
                                            p1.previous = newSpan.getLeft();
                                        }
                                        p1 = newSpan.right;
                                        ret.push(newSpan);
                                        if(flag)
                                            break;
                                        left = findBorder(right, y, 1, (parentLeft - right)/stepX - 1, false)
                                    }
                                    if(dir == 1) {
                                        span.getParent().getLeft().previous = newSpan.getRight();
                                        newSpan.getRight().next = span.getParent().getLeft();
                                    } else {
                                        span.getParent().getLeft().next = newSpan.getRight();
                                        newSpan.getRight().previous = span.getParent().getLeft();
                                    }
                                }
                            }
                            var parentRight = parent.getRight().x
                            if(span.getRight().x > parentRight + stepX) {
                                var right = span.getRight().x;
                                zCurrent = Z(right, y);
                                if(zCurrent > zLevel) {
                                    right = findBorder(right, y, 1, false, true);
                                } else {
                                    right = findBorder(right, y, -1, (span.getRight().x - parentRight)/stepX - 1, true);
                                }
                                if(right && Math.abs(span.getParent().getRight().x - right) > stepX) {
                                    var count = 0, flag = false;
                                    var p1 = span.getRight();
                                    while(right) {
                                        left = findBorder(right, y, -1, (right - parentRight)/stepX - 1, true);
                                        if(!left) {
                                            left = parentRight + step/2;
                                            flag = true;
                                        }
                                        appendCircle2(right, y, 7, 'curve');
                                        appendCircle2(left, y, 7, 'curve');
                                        newSpan = Span(Node(left,y), Node(right,y), level);
                                        newSpan.setParent(span);
                                        if(dir == 1) {
                                            newSpan.getRight().next = p1;
                                            p1.previous = newSpan.getRight();
                                        } else {
                                            p1.next = newSpan.getRight();
                                            newSpan.getRight().previous = p1;
                                        }
                                        p1 = newSpan.left;
                                        ret.push(newSpan);
                                        if(flag)
                                            break;
                                        right = findBorder(left, y, -1, (left - parentRight)/stepX - 1, true);
                                    }
                                    if(dir == 1) {
                                        parent.getRight().next = newSpan.getLeft();
                                    } else {
                                        newSpan.getLeft().next = parent.getRight();
                                    }
                                }
                            }
                        }
                    }
                    var spanPath = "M " + projectX(span.getLeft().x) + " " + projectY(span.getLeft().y) + "L" + projectX(span.getRight().x) + " " + projectY(span.getRight().y);
                    var newSpanPath = "";
                    for(var i = 0; i < ret.length; i++) {
                        var s = ret[i];
                        newSpanPath += "M " + projectX(s.getLeft().x) + " " + projectY(s.getLeft().y) + "L" + projectX(s.getRight().x) + " " + projectY(s.getRight().y);
                    }
                    appendPath(spanPath, 'red', 2, 'class1');
                    if(newSpanPath)
                        appendPath(newSpanPath, 'green', 5, 'class1');
                    if($('#alert').attr('checked'))
                        alert(span.getLevel());
                    $('.class1').remove();
                    return ret;
                }
                var stepX = 0.5, stepY = 1, eps = 0.1, spanFactor = 3;
                var firstPoint = points[1];
                var y  = firstPoint.y;
                var p = Point(firstPoint.x + 0.1, firstPoint.y);
                var pLeft = findBorder(p.x, p.y, -1), pRight = findBorder(p.x, p.y, 1);
                var firstLevel = y;
                appendCircle2(pLeft, y, 4, 'curve');
                appendCircle2(pRight, y , 4, 'curve');
                var leftNode = Node(pLeft, y);
                var rightNode = Node(pRight, y);
                var spanStack = Stack();
                var firstSpan = Span(leftNode, rightNode, 0);
                //findSpans(firstSpan);
                spanStack.push(firstSpan);
                var count = 0;
                while(spanStack.size() > 0) {
                    var span = spanStack.pop();
                    var spans = findSpans(span);
                    count++;
                    if(count > 100)
                        break;
                    for(var i = 0; i < spans.length; i++)
                        spanStack.push(spans[i]);
                }
                var p = firstSpan.getLeft();
                var path = ""
                for(var i = 0; i < 300 ; i++) {
                    path += "M " + projectX(p.x) + " " + projectY(p.y) + "L " + projectX(p.next.x) + " " + projectY(p.next.y);
                    appendPath(path, 'green', 2, 'curve');
                    /*if($('#alert').attr('checked'))
                        alert();*/
                    p = p.next;
                    if(p == firstSpan.getLeft())
                        break;
                }
                for(var i = 0; i< points.length; i++) {
                    appendCircle(points[i], points[i].weight*4,'curve');
                    appendText(points[i].x+0.3, points[i].y, i, 'curve');
                }
            }
            var me = {svg : svg, draw : draw,  drawFunc : drawFunc, drawRays : drawRays, drawPoints : drawPoints,drawSpans : drawSpans};
            return me;
        }
        function Point(X, Y) {
            var x = X, y = Y;
            function distance(point) {
                return Math.sqrt((x - point.x)*(x - point.x) + (y - point.y)*(y - point.y));
            }
            function distanceSqr(point) {
                return (x - point.x)*(x - point.x) + (y - point.y)*(y - point.y);
            }
            function length() {
                return Math.sqrt(x*x + y*y);
            }
            function normalize() {
                var length = Math.sqrt(x*x + y*y), X, Y;
                X = x/length;
                Y = y/length;
                x=X;
                y=Y;
            }
            function setX(X) {x = X};
            function setY(Y) {y = Y};
            function setXY(X, Y) {x = X, y = Y};
            function getX() { return x; }
            function getY() { return y; }
            function normal(direction) {
                if(direction > 0)
                    return Point(-y, x);
                else 
                    return Point(y, -x);
            }
            function toString() {
                return "("+x.toFixed(4)+ ","+y.toFixed(4)+")"
            }
            var me = {
                x : x,
                y : y,
                distance : distance,
                distanceSqr : distanceSqr,
                setX : setX,
                setY : setY,
                setXY : setXY,
                toString : toString,
                normalize : normalize,
                getX : getX,
                getY : getY,
                normal : normal,
                length :length
            };
            return me;
        }
        function Ray(p1/*Начало*/, p2/*Вектор направления*/, flag /*Отсчитывать вектор напавления от нуля - true или от p1 - false*/) {
            var start = Point(p1.x, p1.y), direction;
            if(flag) {
                var length = p2.length();
                direction = Point(p2.x/length, p2.y/length);
            } else {
                var distance = p2.distance(p1);
                direction = Point((p2.x - p1.x)/distance, (p2.y - p1.y)/distance);
            }
            function getPoint(t) {
                return Point(start.x + t*direction.x, start.y + t*direction.y);
            }
            function cross(ray) {
                var znamenatel = ray.direction.y*direction.x - ray.direction.x*direction.y;
                if (znamenatel == 0) {
                    return null;
                }
                var chislitel = ray.direction.y*(ray.start.x - start.x) 
                    + ray.direction.x*(start.y - ray.start.y);
                return chislitel/znamenatel;
            }
            return {
                start : start,
                direction : direction,
                getPoint : getPoint,
                cross: cross
            };
            
        }
        function ZTile(_x, _y, _sizeX, _sizeY, foo) {
            var step = 0.1;
            var sizeX = Math.floor(_sizeX/step)*step, sizeY = Math.floor(_sizeY/step)*step, corner = Point(_x, _y);
            var func = foo;
            var points = [];
            
            
            function getSizeX() { return sizeX; }
            function setSiseX(size) { _sizeX = size; }
            function getSizeY() { return sizeY; }
            function setSiseY(size) { _sizeY = size }
            function getCorner() { return corner; }
            function calcAvg(size/*Размер области (целое число)*/) {
                var sum = 0;
                //console.log(points.length%2)
                var iMin, iMax, jMin, jMax;
                var yLen = points.length;
                var xLen = points[0].length;
                if(yLen%2 != 0) {
                    iMin = Math.floor(yLen/2) - Math.floor(size/2);
                    iMax = Math.floor(yLen/2) + Math.floor(size/2);
                } else {
                    iMin = yLen/2 + Math.floor(-size/2);
                    iMax = yLen/2 + Math.floor(size/2);
                }
                if(xLen%2 != 0) {
                    jMin = Math.floor(xLen/2) - Math.floor(size/2);
                    jMax = Math.floor(xLen/2) + Math.floor(size/2);
                } else {
                    jMin = xLen/2 + Math.floor(-size/2);
                    jMax = xLen/2 + Math.floor((size-1)/2);
                }
                for(var i = iMin; i<=iMax; i++)
                    for(var j = jMin; j<=jMax; j++)
                        sum += points[i][j];
                sum /= (iMax - iMin + 1) * (jMax - jMin + 1);
                return sum;
                //console.log("iMin: " + iMin + " iMax: " + iMax + " jMin: " + jMin + " jMax: " + jMax);
                
            }
            function toString() {
                var ret = "sizeX: " + sizeX + ", sizeY: " + sizeY + "\n";
                for(var i = 0, l = points.length; i < l; i++) {
                    for(var j = 0, ll=points[i].length;j<ll; j++)
                        ret += points[i][j] + " ";
                    ret += "\n";
                }
                return ret;
            }
            function fill() {
                for(var y = corner.y; y <= corner.y + sizeY + 0.001; y += step) {
                    var arr = [];
                    for(var x = corner.x; x <= corner.x + sizeX + 0.001; x += step) {
                        arr.push(foo(x, y));
                    }
                    points.push(arr);
                }
            }
            function findClosestPoint(point) {
                var x = point.x - corner.x, y = point.y - corner.y;
                if(x > sizeX || y > sizeY || x < 0 || y < 0)
                    return;
                return {i : Math.round(y/step), j : Math.round(x/step)};
            }
            function walk(i, j, count, clockwise) {
                var maxI = points.length - 1, maxJ = points[0].length - 1;
                var xDirection, yDirection;
                var iRet, jRet;
                count %= 2*(maxI+maxJ);
                if(!clockwise)
                    count = 2*(maxI + maxJ) - count;
                if(i == 0) {
                    if(count <= j) {
                        return {i : 0, j : j - count}
                    }
                    count -= j;
                    if(count <= maxI) {
                        return {i : count , j : 0}
                    }
                    count -= maxI;
                    if(count <= maxJ) {
                        return {i : maxI, j : count}
                    }
                    count -= maxJ;
                    if(count <= maxI) {
                        return {j : maxJ, i : maxI - count}
                    }
                    count -= maxI;
                    return {i : 0, j : maxJ - count}
                }
                if(i == maxI) {
                    if(count <= maxJ - j) {
                        return {i : maxI, j : j + count};
                    }
                    count -= maxJ - j;
                    if(count <= maxI) {
                        return {i : maxI - count, j : maxJ}
                    }
                    count -= maxI
                    if(count <= maxJ) {
                        return {i : 0, j : maxJ - count}
                    }
                    count -= maxJ;
                    if(count <= maxI) {
                        return {i : count, j : 0 }
                    }
                    count -= maxI;
                    return {i : maxI, j : count}
                }
                if(j == 0) {
                    if(count <= maxI - i) {
                        return {i : i + count, j : 0}
                    }
                    count -= maxI - i;
                    if(count <= maxJ) {
                        return {i : maxI , j: count}
                    }
                    count -= maxJ;
                    if(count <= maxI) {
                        return {i : maxI - count, j : maxJ}
                    }
                    if(count <= maxJ) {
                        return {i : 0, j : count}
                    }
                    return {i : count, j : 0}
                }
                if(j == maxJ) {
                    if(count <= i) {
                        return {i : i - count, j : maxJ}
                    }
                    count -= i;
                    if(count <= maxJ) {
                        return {i : 0, j : count}
                    }
                    count -= maxJ;
                    if(count <= maxI) {
                        return {i : count, j : 0}
                    }
                    count -= maxI;
                    if(count <= maxJ) {
                        return {i : maxI, j : count}
                    }
                    return {i : maxI - count, j: maxJ}
                }
            }
            var me = {
                getSizeX : getSizeX,
                setSiseX : setSiseX,
                getSizeY : getSizeY,
                setSiseY : setSiseY,
                getCorner : getCorner,
                fill : fill,
                toString : toString,
                calcAvg : calcAvg,
                points : points,
                corner: corner,
                findClosestPoint : findClosestPoint,
                step : step,
                walk : walk
            };
            return me;
            
        }
        function Span(l, r , lvl) {
            var level = lvl, left = l, right = r, parent = null;
            function setParent(Parent) {
                parent = Parent;
            }
            function getParent() {
                return parent;
            }
            function getLeft() {
                return left;
            }
            function getRight() {
                return right;
            }
            function getLevel() {
                return level;
            }
            function getLength() {
                return r.x - l.x;
            }
            var me = {
                setParent : setParent,
                getParent : getParent,
                getLevel : getLevel,
                getLeft : getLeft,
                getRight : getRight,
                getLength : getLength,
                level : lvl,
                left : left,
                right : right
            };
            return me;
        }
        function Node(x, y) {
            return {x : x, y : y, next : null, previous : null }
        }
        function Stack() {
            var data = [];
            function peek() {
                if(!data.length)
                    return null;
                return data[data.length - 1];
            }
            function push(element) {
                data.push(element);
            }
            function pop() {
                return data.pop();
            }
            function size() { return data.length; }
            return {peek : peek, push : push, pop : pop, size : size}
        }
    </script>
    <script>
        function resolve(x1, x2, z1, z2, y, level, eps, func, iteration, isLeft) {
            var k, b, x, z;
            while(iteration != 0) {
                // coefficient for linear equation
                k = (z2 - z1)/(x2 - x1);
				if(isNaN(k))
					return k;
				b = k*x1 - z1;
				// expected root
				x = (level + b)/k;
				if(iteration == 0)
					return x;
				z = func(x, y);
                if(Math.abs(z - level) < eps)
                    break;
                if(z > level) {
                    x2 = x;
                    z2 = z;
                } else {
                    x1 = x;
                    z1 = z;
                }
                iteration--;
            }
            if(typeof(isLeft) != 'undefined')
                if((z < level && k > 0) || (z > level && k < 0)) {
                    if(!isLeft) {
                        while(k * (z - level) < 0) {
                            x += eps/3;
                            z = func(x, y);
                        }
					}
				} else if(isLeft) {
					while(k * (z - level) > 0) {
						x -= eps/3;
						z = func(x, y);
					}
				}
            return x;
        }
    </script>
</html>
