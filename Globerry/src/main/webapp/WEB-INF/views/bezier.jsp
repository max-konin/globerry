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
            min : 1,
            max : 10,
            step : 0.02,
            value : 3.84,
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
            graph.drawIsoline(points, value);
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
            graph = Graph(1000,1000,-4,4,-2,4,50);
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
            point.weight = 2;
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
            //graph.draw3D(points, 3.84);
            graph.drawIsoline(points,3);
            
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
            
            function drawIsoline(points, level) {
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
                function gradientDescent(fromX, fromY) {
                    var x1 = fromX, y1 = fromY, x2, y2;
                    var z1 = Z(x1, y1), z2;
                    var count = 0;
                    var travelLength = 0;
                    var direction;
                    var zshtrih;
                    //var prevDirection = direction;
                    if(z1 < level)
                        direction = 1;
                    else
                        direction = -1;
                    while(true) {
                        zshtrih = dZdxy(x1, y1);
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
                            }
                            break;
                        }
                        
                        x1 = x2, y1 = y2, z1 = z2;
                        count++;
                        if(count > 100)
                            break;
                    }   
                    return Point(x2,y2);
                }
                function getTile(point) {
                    for(var i = 0, l = tiles.length; i<l; i++) {
                        var distance = point.distance(tiles[i].getCorner());
                        if(distance < 0.2)
                            return tiles[i];
                    }
                    return false;
                }
                function findCrossHorizontal(p1, p2, p3, l) {
                    var t1, t2;
                    var y2y1 = p2.y - p1.y;
                    if(Math.abs(y2y1) < 0.0001)
                        return null;
                    t1 = (p3.y - p1.y)/y2y1;
                    t2 = (p1.x + t1*(p2.x - p1.x) - p3.x)/l;
                    return {t1 : t1, t2 : t2};
                }
                function findCrossVertical(p1, p2, p3, l) {
                    var t1, t2;
                    var x2x1 = p2.x - p1.x;
                    if(Math.abs(x2x1) < 0.0001)
                        return null;
                    t1 = (p3.x - p1.x)/(p2.x - p1.x);
                    t2 = (p1.y + (p2.y - p1.y)*t1 - p3.y)/l;
                    return {t1 : t1, t2 : t2 };
                }
                //Переменные алгоритма
                var z,z_shtrih, x, y, zAside, xAside, yAside, zAside_shtrih, zNew, zNew_shtrih, xNew, yNew, count = 0;
                var zSusp = 2;
                var tiles = [], tileMapSize = 0.8;
                var rays = [];
                //Константы алгортима
                var dxdy = Point(0.05, 0.05), step = Point(0.5,0.5), eps = 0.05;
                //Возвращаемые значения
                var rays = [];
                
                
                var path = "";
                var tilesPath = "";
                xAside = points[0].x - 0.05, yAside = points[0].y - 0.05;
                zAside = Z(xAside, yAside);
                zAside_shtrih = dZdxy(xAside, yAside);
                while(true) {
                    
                    path = "M " + projectX(xAside) + " " + projectY(yAside);
                    var p = gradientDescent(xAside, yAside);
                    
                    x = p.x, y = p.y;
                    z_shtrih = dZdxy(x, y);
                    
                    if(z_shtrih.length() < 1.6 && level > 3) {
                        //appendCircle(p, 5, 'curve')
                        var xTile, yTile, tile;
                        if(z_shtrih.x > 0)
                            xTile = x - tileMapSize*0.2;
                        else
                            xTile = x - tileMapSize*0.8;
                        if(z_shtrih.y > 0)
                            yTile = y - tileMapSize*0.2;
                        else
                            yTile = y - tileMapSize*0.8;
                        tile = getTile(Point(xTile, yTile));
                        if(!tile) {
                            tile = ZTile(xTile, yTile, tileMapSize, tileMapSize, Z);
                            tile.fill();
                            tiles.push(tile);
                            tilesPath = "M " + projectX(tile.getCorner().x) + " " +projectY(tile.getCorner().y) +
                                "H " + projectX(tile.getCorner().x + tile.getSizeX()) + " " + 
                                "V " + projectY(tile.getCorner().y + tile.getSizeY()) + " " +
                                "H " + projectX(tile.getCorner().x) + " " + 
                                "V " + projectY(tile.getCorner().y);
                            appendPath(tilesPath,'black', 2, 'curve');
                        }
                        tileMapSize = tile.getSizeX();
                        var avg = tile.calcAvg(2);
                            var indexes = tile.findClosestPoint(p);
                            
                            if(indexes) {
                                //appendCircle(Point(indexes.j*tile.step + tile.corner.x, indexes.i*tile.step + tile.corner.y), 3, 'curve');
                                var prevPoint = rays[rays.length - 1].start;
                                var t = findCrossVertical(prevPoint, p, tile.corner, tileMapSize), point;
                                if(t &&  t.t1 < 1 && t.t2 > 0 && t.t2 < 1) {
                                    point = Point(tile.corner.x, tile.corner.y + t.t2*tileMapSize);
                                } else {
                                    t = findCrossVertical(prevPoint, p, Point(tile.corner.x + tileMapSize, tile.corner.y), tileMapSize);
                                    if(t &&  t.t1 < 1 && t.t2 > 0 && t.t2 < 1) {
                                        point = Point(tile.corner.x + tileMapSize, tile.corner.y + tileMapSize*t.t2);
                                    } else {
                                        t = findCrossHorizontal(prevPoint, p,tile.corner, tileMapSize);
                                        if(t &&  t.t1 < 1 && t.t2 > 0 && t.t2 < 1) {
                                            point = Point(tile.corner.x + tileMapSize*t.t2, tile.corner.y);
                                        } else {
                                            t = findCrossHorizontal(prevPoint, p, Point(tile.corner.x, tile.corner.y + tileMapSize), tileMapSize);
                                            //point = Point(tile.corner.x + t.t2*tileMapSize, tile.corner.y + tileMapSize);
                                            point = Point(tile.corner.x + tileMapSize*t.t2, tile.corner.y + tileMapSize);
                                        }
                                    }
                                }
                                
                                //appendCircle(prevPoint, 3, 'curve');
                                //appendCircle(p, 3, 'curve');
                                appendCircle(point, 5, 'curve');
                                var startPoint = tile.findClosestPoint(point);
                                if(level < avg) {
                                    /*var index1, index2, index, clockwise;
                                    if(tile.points[startPoint.i][startPoint.j] > level) {
                                        for(var i = 0; ; i++) {
                                            index1 = tile.walk(startPoint.i, startPoint.j, i, true);
                                            index2 = tile.walk(startPoint.i, startPoint.j, i, false);
                                            if(tile.points[index1.i][index1.j] < level) {
                                                index = index1;
                                                clockwise = true;
                                                break;
                                            }
                                            if(tile.points[index2.i][index2.j] < level) {
                                                index = index2;
                                                clockwise = false;
                                                break;
                                            }
                                        }
                                    }*/
                                    if(tile.points[startPoint.i][startPoint.j] < level) {
                                        var index = startPoint;
                                        for(var i = 1; tile.points[index.i][index.j] < level; i++) {
                                            index = tile.walk(index.i,index.j,i, true);
                                            appendCircle(Point(tile.corner.x + tile.step*index.j, tile.corner.y + tile.step*index.i), 7, 'toDelete');
                                            if($('#alert').attr('checked'))
                                                alert(index.i + " " + index.j);
                                            $('.toDelete').remove();
                                        }
                                        x = tile.corner.x + tile.step*index.j;
                                        y = tile.corner.y + tile.step*index.i;
                                        z_shtrih = dZdxy(x, y);
                                        //console.log(index);
                                    }
                                        
                                }
                            } else {
                                console.log(tile.toString());
                                console.log(Point(xTile, yTile).toString());
                            }
                            
                    }
                    var ray = Ray(p, Point(-z_shtrih.getY(), z_shtrih.getX()), true);
                    z_shtrih.normalize();
                    xAside = x - z_shtrih.getY()*step.x;
                    yAside = y + z_shtrih.getX()*step.y;
                    path += "L " + projectX(x) + " " + projectY(y) + "L " + projectX(xAside) + " " + projectY(yAside);
                    
                    appendPath(path,'green', 1, 'curve');
                    
                    //if($('#alert').attr('checked'))
                    //    alert(z_shtrih.length());
                    count++;
                    rays.push(ray);
                    if(count > 20)
                        break;
                }
                
                for(var tileCounter = 0; tileCounter < tiles.length; tileCounter++) {
                    var tile = tiles[tileCounter];
                    var corner = tile.corner;
                    for(var i = 0; i < tile.points.length; i++) {
                        for(var j = 0; j < tile.points[0].length; j++) {
                            var step = tile.step;
                            var point = Point(corner.x + j*step, corner.y + i*step);
                            var clazz = tile.points[i][j] > level ? 'positive' : 'negative';
                            clazz += " curve";
                            appendCircle(point, 2, clazz);
                        }
                    }
                    //appendCircle(point, 2, 'positive curve');
                }
                drawRays(rays);
                
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
                function findBorder(p, direction) {
                    var x = p.x, y = p.y, z = Z(x, y);
                    var count = 0;
                    var sign = z - zLevel;
                    var xPrev = x, zPrev = z;
                    while(true) {
                        x = xPrev + direction*stepX;
                        z = Z(x, y);
                        count++;
                        if(sign*(z - zLevel) < 0 || count > 100)
                            break;
                        xPrev = x;
                        zPrev = z;
                    }
                    var x0 = resolve(x, xPrev, z, zPrev, y, zLevel, eps, Z, 50);
                    return Point(x0, y);
                }
                function findSpans(span) {
                    var parent = span.getParent();
                    var dir = -1;
                    for(var i = 0; i < 1; i++) {
                        dir *= -1;
                        var level = span.getLevel() + dir;
                        var x, y = level*stepY;
                        if(level != parent.getLevel()) {
                            x = span.getLeft();
                           
                        }
                    }
                    return [];
                }
                var stepX = 0.3, stepY = 0.3, eps = 0.1;
                var y  = points[0].y;
                var p = Point(points[0].x + 0.1, points[0].y);
                var pLeft = findBorder(p, 1), pRight = findBorder(p, -1);
                var leftNode = Node(pLeft.x, pLeft.y);
                var rightNode = Node(pRight.x, pRight.y);
                var spanStack = Stack();
                var firstSpan = Span(leftNode, rightNode, 0);
                spanStack.push(firstSpan);
                appendCircle(pLeft, 3, 'curve');
                appendCircle(pRight, 3, 'curve');
                
            }
            var me = {svg : svg, draw : draw,  drawFunc : drawFunc, drawRays : drawRays, drawPoints : drawPoints, drawIsoline : drawIsoline,drawSpans : drawSpans};
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
            function getParent() {} { return parent; }
            function getLevel() { return level; }
            function setLevel(Level) { level = Level; }
            function getRight() { return right; }
            function getLeft() {return left; }
            return {
                setParent : setParent,
                getParent : getParent,
                getLevel : getLevel,
                getRight : getRight,
                getLeft : getLeft
            }
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
		function resolve(x1, x2, z1, z2, y, level, eps, func, iteration) {
			// coefficient for linear equation
			var k = (z2 - z1)/(x2 - x1);
			if(isNaN(k))
				return k;
			var b = k*x1 - z1;
			// expected root
			var x = (level + b)/k;
			if(iteration == 0)
				return x;
			var z = func(x, y);
			if(Math.abs(z - level) < eps)
				return x;
			if(z > level)
				x = resolve(x1, x, z1, z, y, level, eps, func, iteration - 1);
			else
				x = resolve(x, x2, z, z2, y, level, eps, func, iteration - 1);
			return x;
		}
	</script>
</html>
	