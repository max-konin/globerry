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
        </style>
    </head>
    <body>
        <h1>Hello World!</h1>
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
        function SVG(h, w) {
            var NS="http://www.w3.org/2000/svg";
            var svg=createElement('svg')
            svg.width=w;
            svg.height=h;
            return svg;
        }
        
        function createAxis(w, h, scaleX, scaleY) {
            
            var pix = 40, axisOffset = 10;
            var horizontalCount = w/pix, verticalCount = h/pix;
            var g = createElement('g');
            var linesPath = ""
            var text = createElement('text');
            var tspan;
            var tspanScaled;
            var axisPath = "M 0 0 h " + w + "M 0 0 v " + h + "M 0 " + h + " h "+ w + "M " + w + " 0" + "v " + h;
            for(var i = 1; i < horizontalCount; i++) {
                linesPath += "M " + i*pix + " 0";
                linesPath += "v " + h;
                tspan = createElement('tspan');
                tspan.setAttribute('x', i*pix - 10);
                tspan.setAttribute('y', h + 3*axisOffset);
                tspan.textContent = i*pix;
                text.appendChild(tspan);
                
                tspanScaled = createElement('tspan');
                tspanScaled.setAttribute('x', i*pix - 10);
                tspanScaled.setAttribute('y', h + axisOffset);
                tspanScaled.textContent = scaleX*i*pix/w;
                text.appendChild(tspanScaled);
            }
            for(var i = 1; i < verticalCount; i++) {
                linesPath += "M " + "0 " + i*pix;
                linesPath += "h " + w;
                
                tspan = createElement('tspan');
                tspan.setAttribute('x', w + 5*axisOffset);
                tspan.setAttribute('y', i*pix + 5);
                tspan.textContent = i*pix;
                text.appendChild(tspan);
                
                tspanScaled = createElement('tspan');
                tspanScaled.setAttribute('x', w + axisOffset);
                tspanScaled.setAttribute('y', i*pix + 5);
                tspanScaled.textContent = scaleY*i*pix/h;
                text.appendChild(tspanScaled);
                
                text.appendChild(tspanScaled);
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
        
        
        function calc(x) {
            var y = Math.sin(x);
            var x_ = 1;
            var y_ = (Math.sin(x + 0.01) - Math.sin(x))/0.01;
            return [new Point(x, y), new Point(x_ , y_)];
        }
        
        function viz() {
            
        }
        
        $(document).ready(function() {
            var w = 500, h = 500;
            var svg=SVG(w, h);
            var g = createElement('g');
            
            
            var path = createElement('path');
            
            var defs = createElement('defs');
            svg.appendChild(defs);
            
            g.appendChild(path);
            svg.appendChild(g);
            
            var axis = createAxis(w, h, 2, 2);
            
            
            
            var gradient = createElement('radialGradient');
            defs.appendChild(gradient);
            gradient.setAttribute('id', 'grad1');
            
            gradient.setAttribute('cx', '50%');
            gradient.setAttribute('cy', '50%');
            gradient.setAttribute('r', '50%');
            gradient.setAttribute('fx', '50%');
            gradient.setAttribute('fy', '50%');
            
            var stop = createElement('stop');
            stop.setAttribute('offset', '0%');
            stop.setAttribute('style', 'stop-color:orange;stop-opacity:1');
            gradient.appendChild(stop);
            
            stop = createElement('stop');
            stop.setAttribute('offset', '95%');
            stop.setAttribute('style', 'stop-color:orange;stop-opacity:0');
            gradient.appendChild(stop);
            stop = createElement('stop');
            stop.setAttribute('offset', '100%');
            stop.setAttribute('style', 'stop-color:orange;stop-opacity:0.5');
            gradient.appendChild(stop);
            
            
            svg.appendChild(axis);
            
            var circle = createElement('circle');
            circle.setAttribute('cx', 100);
            circle.setAttribute('cy', 100);
            circle.setAttribute('r', 50);
            circle.setAttribute('fill', 'url(#grad1)');
            
            g.appendChild(circle);
            
            $('#pic').append(svg);
            
        });
        
    </script>
</html>
