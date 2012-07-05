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
            min : 1,
            max : 10,
            step : 0.02,
            value : 2.7,
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
            graph.draw3D(points, value);
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
            graph = Graph(1000,1000,-4,4,-4,4,50);
            $('#pic').append(graph.svg);
            //graph.draw(foo, 0.1);
            //graph.drawFunc(foo, 2);
            points = [];
            var point = Point(0, 0);
            point.weight = 1;
            points.push(point);
            
            point = Point(1, 1.5);
            point.weight = 1;
            points.push(point);
            
            point = Point(2, 0);
            point.weight = 1;
            points.push(point);
            
            /*point = Point(3,1)
            point.weight = 4;
            points.push(point);
            
            point = Point(1.5,1)
            point.weight = 2;
            points.push(point);
            
            point = Point(-1, -3);
            point.weight = 2;
            points.push(point);*/
            //graph.drawRays(rays);
            graph.draw3D(points, parseFloat($('#minVal').val()));
            //graph.drawPoints(points);
            
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
                for(var i = 1, l = arr.length; i < l; i++) {
                    ray2 = arr[i];
                    var t1 = ray1.cross(ray2);
                    var t2 = ray2.cross(ray1);
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
                svg.appendChild(circle)
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
            function draw3D(points, level) {
                function Z(x, y) {
                    var val = 0;
                    for(var i = 0, l = points.length; i < l; i++) {
                        var point = points[i];
                        val += point.weight/(Math.sqrt((point.x - x)*(point.x - x) + (point.y - y)*(point.y - y)));
                    }
                    return val;
                }
                function Z_shtrih(x, y) {
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
                //Движется в направлении градиента или антиградиента к заданному уровню (level).
                function gradientDescent(fromX, fromY) {
                    var x1 = fromX, y1 = fromY, x2, y2;
                    var z1 = Z(x1, y1), z2;
                    var count = 0;
                    var travelLength = 0;
                    if(z1 < level)
                        direction = 1;
                    else
                        direction = -1;
                    while(true) {
                        zshtrih = Z_shtrih(x1, y1);
                        zshtrih.normalize();
                        var dx = direction*zshtrih.getX()*dxdy.x;
                        var dy = direction*zshtrih.getY()*dxdy.y;
                        travelLength += Math.sqrt(dx*dx + dy*dy);
                        x2 = x1 + dx;
                        y2 = y1 + dy;
                        z2 = Z(x2, y2);
                        if(z2*direction > direction*level) {
                            if(Math.abs(z2 - level) > eps) {
                                x2 = (level - z1)/(z2 - z1)*(x2 - x1) + x1;
                                y2 = (level - z1)/(z2 - z1)*(y2 - y1) + y1;
                                //console.log("Upating point from " + z2 + " to " + Z(x2, y2));
                            }
                            break;
                        }
                        
                        x1 = x2, y1 = y2, z1 = z2;
                        count++;
                        if(count > 100)
                            break;
                    }
                    if(travelLength < stepFactor) {
                        stepX += stepX*dStep;
                        stepY += stepY*dStep;
                    } else {
                        stepX -= stepX*dStep;
                        stepY -= stepY*dStep;
                    }
                        
                    return Point(x2,y2);
                }
                //var x = (maxX - minX)/2, y = (maxY - minY)/2;
                
                function checkSedlo() {
                    var d = 0.005;
                    var zshtrih2 = Z_shtrih(expectedX, expectedY);
                    if(zshtrih.x*zshtrih2.x < 0 && zshtrih.y*zshtrih2.y < 0)
                        return true;
                    return false;
                }
                var eps = 0.1, d = 1, dxdy = Point(0.05, 0.05) //Шаг в алгоритме скорейшего спуска.;
                var dStep = 0.2;//Число, с которым увеличивается stepX, stepY.
                var stepFactor = 0.4;
                var point = points[0];
                var stepX = 0.5, stepY = 0.5;
                //var x = 0, y = -0.4;
                var x = point.x + 0.1, y = point.y + 0.1;
                var path = "M "+projectX(x)+" "+projectY(y);
                var tangentPath = "";
                var direction;
                var rays = [];
                var firstPoint = gradientDescent(x,y), newPoint = firstPoint, zshtrih = Z_shtrih(newPoint.x, newPoint.y);
                var expectedX, expectedY;
                var firstRay;
                for(var i = 0; i < 50; i++) {
                    
                    newPoint = gradientDescent(x, y);
                    zshtrih = Z_shtrih(newPoint.x, newPoint.y);
                    var len = zshtrih.length();
                    zshtrih.normalize();
                    var ray = Ray(newPoint, Point(-zshtrih.getY(), zshtrih.getX()), true);
                    if(!firstRay)
                        firstRay = ray
                    rays.push(ray);
                    path = "M" + projectX(x) + " " + projectY(y);
                    for(var j = 1; j < 10; j++) {
                        expectedX = newPoint.x - zshtrih.getY()*stepX/j;
                        expectedY = newPoint.y + zshtrih.getX()*stepY/j;
                        var sedlo = checkSedlo();
                        if(!sedlo)
                            break;
                    }
                    var currentRay = ray;
                    x = expectedX;
                    y = expectedY;
                    
                    var zCurrent = Z(newPoint.x,newPoint.y);
                    console.log(zCurrent +" - current Z");
                    
                    path += "L " + projectX(newPoint.x) + " " + projectY(newPoint.y) + "L " + projectX(x) + " " + projectY(y);
                    
                    tangentPath = "M " + projectX(newPoint.x) + " " + projectY(newPoint.y) + "L " + projectX(newPoint.x+zshtrih.getX()) + " " + projectY(newPoint.y+zshtrih.getY());
                    
                    appendText(newPoint.x + 0.1, newPoint.y, zCurrent.toFixed(2), 'curve');
                    appendText(newPoint.x - 0.3, newPoint.y, i, 'curve');
                    appendCircle(newPoint, 7, 'curve');
                    appendPath(path,'green', 3, 'curve');
                    //appendPath(tangentPath,'red',1,'curve');
                    
                    if($('#alert').attr('checked'))
                        alert(sedlo);
                    
                    var currentRay = Ray(Point(x,y), Point(-zshtrih.getY(), zshtrih.getX()));
                    var t1 = firstRay.cross(ray), t2 = ray.cross(firstRay);
                    console.log(t1 +" "+t2);
                    if(t1 > -1 && t1 < 1 && t2 < 1 && t2 > 0.1 && i > 2)
                        break;
                    if(t1 > -0.2 && t1 < 0 && t2 < 0 && t2 > -0.2)
                        break;
                    
                    /*if(firstPoint.distance(newPoint) < 0.3)
                        if(i!=0)
                            break;
                    */
                   
                }
                
                //appendPath(path,'green', 3, 'curve');
                //appendPath(tangentPath,'red',1);
                drawRays(rays, false, 'curve');
                //gradientDescent(0, -1.1);
                
            }
            var me = {svg : svg, draw : draw,  drawFunc : drawFunc, draw3D : draw3D, drawRays : drawRays, drawPoints : drawPoints};
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
    </script>
</html>
