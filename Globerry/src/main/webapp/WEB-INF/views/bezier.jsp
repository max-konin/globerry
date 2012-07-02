<%-- 
    Document   : bezier
    Created on : 21.06.2012, 11:37:08
    Author     : Ed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
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
        <div id="wolfram"></div>
        <div id="pic"></div>
    </body>
    <script>
        
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
            var graph = Graph(500,500,-2,2,-2,2,50);
            $('#pic').append(graph.svg);
            //graph.draw(foo, 0.1);
            //graph.drawFunc(foo, 2);
            var points = [];
            var point = Point(-1, 2);
            point.weight = 1;
            points.push(point);
            
            point = Point(0, 2);
            point.weight = 1;
            points.push(point);
            
            point = Point(1, 0);
            point.weight = 1;
            points.push(point);
            
            
            point = Point(2, 2);
            point.weight = 2;
            points.push(point);
            
            var rays = [];
            var ray = Ray(Point(1, 0), Point(1,1));
            rays.push(ray);
            
            ray = Ray(Point(0, 1), Point(-1,1));
            rays.push(ray);
            
            ray = Ray(Point(-1, 0), Point(-1,-1));
            rays.push(ray);
            
            ray = Ray(Point(0, -1), Point(1,-1));
            rays.push(ray);
            
            graph.drawRays(rays);
            
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
            function drawFunc(func, step) {
                var rays = [];
                for(var x = minX; x <= maxX ; x+= step) {
                    var d = x + 0.05*step;
                    rays.push(Ray(Point(x, func(x)),Point(d, func(d))));
                }
                drawRays(rays);
            }
            function drawRays(arr/*Массив лучей, начало луча - точка функции, направление - касетельная в этой точке*/) {
                if(arr.length <= 1)
                    return;
                var ray1 = arr[0], ray2, c1, c2, factor = 1, prevT = null;
                var path = "", pathVect = "";
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
                       
                    if(t1 >= 0 && t2 <= 0) {
                        c1 = ray1.getPoint(prevT*0.7);
                        c2 = ray2.getPoint(t2);
                        prevT = -t2;
                    } else if(t1 >= 0 && t2 >= 0) {
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
                    path += "M " + projectX(ray1.start.x) + " " + projectY(ray1.start.y) + "C " + projectX(c1.x) + " " + projectY(c1.y) + " "
                            + projectX(c2.x) + " " + projectY(c2.y) + " "
                            + projectX(ray2.start.x) + " " + projectY(ray2.start.y);
                    ray1 = ray2;
                    
                    appendCircle(c1);
                    appendCircle(c2);
                    appendCircle(ray1.start);
                }
                var gr = createElement('path');
                gr.setAttribute('d', path);
                gr.setAttribute('fill', 'none');
                gr.setAttribute('stroke', 'red');
                gr.setAttribute('stroke-width',3);
                svg.appendChild(gr);
                
                gr = createElement('path');
                gr.setAttribute('d', pathVect);
                gr.setAttribute('fill', 'none');
                gr.setAttribute('stroke', 'black');
                gr.setAttribute('stroke-width',1);
                
                
                svg.appendChild(gr);
            
            }
            function appendCircle(point) {
                var circle = createElement('circle');
                circle.setAttribute('class', 'connect');
                circle.setAttribute('cx', projectX(point.x));
                circle.setAttribute('cy', projectY(point.y));
                circle.setAttribute('r', 3);
                svg.appendChild(circle)
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
                //var x = (maxX - minX)/2, y = (maxY - minY)/2;
                var eps = 0.2, d = 1;
                var point = points[0];
                var x = point.x, y = point.y;
                var dx, dy;
                var z = Z(x,y);
                while(z > level - eps) {
                    
                }
                
            }
            var me = {svg : svg, draw : draw,  drawFunc : drawFunc, draw3D : draw3D, drawRays : drawRays};
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
            function setX(X) {x = X};
            function setY(Y) {y = Y};
            function setXY(X, Y) {x = X, y = Y};
            var me = {
                x : x,
                y : y,
                distance : distance,
                distanceSqr : distanceSqr,
                setX : setX,
                setY : setY,
                setXY : setXY
                };
            return me;
        }
        function Ray(p1/*Начало*/, p2/*Вектор направления*/) {
            var start = Point(p1.x, p1.y), direction;
            var distance = p2.distance(p1);
            direction = Point((p2.x - p1.x)/distance, (p2.y - p1.y)/distance);
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
