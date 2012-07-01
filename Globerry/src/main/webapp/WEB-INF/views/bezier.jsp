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
            return Math.sin(x);
        }
        
        
        $(document).ready(function() {
            var graph = Graph(600,500,0,10,-2,2,50);
            graph.draw(foo, 0.1);
            graph.drawBezier(foo, 2);
            $('#pic').append(graph.svg);
            
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
            function drawBezier(func, step) {
                
                var factor = 0.5;
                var path = "M" + projectX(minX) + " " + projectY(func(minX)), d = minX+0.05*step;
                var currentPoint = Point(minX, func(minX)), tmp = Point(d, foo(d));
                var currentTangent = Point((tmp.x - currentPoint.x)/d, (tmp.y - currentPoint.y)/d);
                var nextPoint, nextTangent, ray1, ray2, c1, c2;
                for(var x = minX + step; x <= maxX ; x+= step) {
                    d = x + 0.05*step;
                    tmp = Point(d, foo(d));
                    nextPoint = Point(x, foo(x));
                    nextTangent = Point(tmp.x, tmp.y);
                    ray1 = Ray(currentPoint, currentTangent), ray2 = Ray(nextPoint, nextTangent);
                    var p = ray1.getPoint(1);
                    //path += "M " + projectX(ray1.start.x) + " " + projectY(ray1.start.y) + 
                    //     "L " + projectX(p.x) + " " + projectY(p.y);
                    
                    var t = ray1.cross(ray2);
                    if(t >= 0) {
                        c1 = ray1.getPoint(t);
                        c2 = c1;
                        //;
                        var circle = createElement('circle');
                        circle.setAttribute('class', 'connect');
                        circle.setAttribute('cx', projectX(c1.x));
                        circle.setAttribute('cy', projectY(c1.y));
                        circle.setAttribute('r', 5);
                        svg.appendChild(circle);
                    } else if(t <= 0) {
                        c1 = ray1.getPoint(factor);
                        c2 = ray2.getPoint(factor);
                    }
                    path += "M " + projectX(ray1.start.x) + " " + projectY(ray1.start.y) + "C " + projectX(c1.x) + " " + projectY(c1.y) + " "
                            + projectX(c2.x) + " " + projectY(c2.y) + " "
                            + projectX(nextPoint.x) + " " + projectY(nextPoint.y);
                    currentPoint = nextPoint;
                    currentTangent = nextTangent;
                }
                var s1 = Point(3, 3), s2 = Point(0,0), d1 = Point(3,0), d2 = Point(-5,0);
                var ray1 = Ray(s1, d1), ray2 = Ray(s2, d2);
                var t = ray1.cross(ray2);
                console.log(t);
                console.log(ray1.getPoint(t));
                var gr = createElement('path');
                gr.setAttribute('d', path);
                gr.setAttribute('fill', 'none');
                gr.setAttribute('stroke', 'red');
                gr.setAttribute('stroke-width',1);
                
                /*var circle = createElement('circle');
                circle.setAttribute('class', 'connect');
                circle.setAttribute('cx', c1.x);
                circle.setAttribute('cy', c1.y);
                circle.setAttribute('r', 5);
                svg.appendChild(circle);
                
                circle = createElement('circle');
                circle.setAttribute('class', 'connect');
                circle.setAttribute('cx', c2.x);
                circle.setAttribute('cy', c2.y);
                circle.setAttribute('r', 5);
                svg.appendChild(circle);*/
                
                svg.appendChild(gr);   
            }
            function draw3D(func) {
                
            }
            var me = {svg : svg, draw : draw, drawBezier : drawBezier};
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
