/**
 * Принимает на вход функцию Z от (x,y) и возвращает связный список точек, которые лежат на изолинни с уровнем zLevel, 
 * окружающие первыю точку.
 * drawCircle - это функция рисования кружочков, нужна для дебага.
 * _stepX, _stepY - вынос в глобальные параметры размеров шагов
 */
function calculatePoints(startPoint, zLevel, Z, drawCircle, _stepX, _stepY) {
    /**
     * Находит границу изолинии слева или справа от точки (_x,_y), с точностью eps, если граница не найдена, то
     * возвращается null.
     * direction - направлениепоиска, может принимать значения 1 - поиск вправо и -1 - влево.
     * stepCount - количество шагов размера stepX для поиска границы, если не задано, то stepCount полагается равным 1000.
     * positionFlag - с какой стороны будет находиться точка, если true, то слева от границы, false - справа, если не
     * задано то с любой.
     */
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
			//console.log("z - zLevel = " + (z - zLevel));
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
            x0 = resolve(x, xPrev, z, zPrev, y, eps, 50);
        } else {
            x0 = resolve(x, xPrev, z, zPrev, y, eps, 50, positionFlag);
        }
        return x0;
    }
    function findSpans(span) {
        var parent = span.getParent();
                    
        var dir = -1, ret = [];
        var parentLevel = parent ? parent.getLevel() : null, flag = false;
        var debug = typeof(drawCircle) != 'undefined';
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
                var stepCount = span.getRight().x - span.getLeft().x > 4*stepX ? false : 8 + stepY/stepX*2;
                while(left) {
                    right = findBorder(left, y, 1, stepCount, false);
					/////////////////////////////////
					if(right == null)
						return null;
					/////////////////////////////////
                    if(flag && left - ret[ret.length - 1].getRight().x < stepX) {
                        ret[ret.length - 1].getRight().x = right;
                        left = findBorder(right, y, 1,(span.getRight().x - right)/stepX - 2, false);
                        continue;
                    }
                    if(debug) {
						drawCircle(left, y, 3, 'curve', 'black', 3);
						drawCircle(right, y, 3, 'curve', 'black', 3);
                    }            
                                    
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
                            if(debug) {
                                drawCircle(right, y, 3, 'curve', 'black', 3);
                                drawCircle(left, y, 3, 'curve', 'black', 3);
                            }
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
        /*var spanPath = "M " + projectX(span.getLeft().x) + " " + projectY(span.getLeft().y) + "L" + projectX(span.getRight().x) + " " + projectY(span.getRight().y);
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
        $('.class1').remove();*/
        return ret;
    }
    function resolve(x1, x2, z1, z2, y, eps, iteration, isLeft) {
        var k, b, x, z;
        while(iteration != 0) {
            // coefficient for linear equation
            k = (z2 - z1)/(x2 - x1);
            if(isNaN(k))
                return x;
            b = k*x1 - z1;
            // expected root
            x = (zLevel + b)/k;
            if(iteration == 0)
                return x;
            z = Z(x, y);
            if(Math.abs(z - zLevel) < eps)
                break;
            if(z > zLevel) {
                x2 = x;
                z2 = z;
            } else {
                x1 = x;
                z1 = z;
            }
            iteration--;
        }
        if(typeof(isLeft) != 'undefined')
            if((z < zLevel && k > 0) || (z > zLevel && k < 0)) {
                if(!isLeft) {
                    while(k * (z - zLevel) < 0) {
                        x += eps/3;
                        z = Z(x, y);
                    }
                }
            } else if(isLeft) {
                while(k * (z - zLevel) > 0) {
                    x -= eps/3;
                    z = Z(x, y);
                }
            }
        return x;
    }
    var eps = 0.1,
		stepX = typeof(_stepX) == 'undefined' ? 0.15 : _stepX, 
		stepY = typeof(_stepY) == 'undefined' ? 0.7 : _stepY;
    
    var p = startPoint, y = startPoint.y;
    var pLeft = findBorder(p.x, p.y, -1), pRight = findBorder(p.x, p.y, 1);
	if(pLeft == null || pRight == null) {
		//drawCircle(p.x, p.y);
		return null;
		//pLeft = findBorder(p.x, p.y, -1);
		//pRight = findBorder(p.x, p.y, 1);
	}
    var firstLevel = y;
    
    var leftNode = Node(pLeft, y);
    var rightNode = Node(pRight, y);
    var spanStack = Stack();
    var firstSpan = Span(leftNode, rightNode, 0);
    spanStack.push(firstSpan);
    var count = 0;
    while(spanStack.size() > 0) {
        var span = spanStack.pop();
        var spans = findSpans(span);
        count++;
        if(count > 1000)
            break;
		//////////////////////
		if(spans == null)
			continue;
		//////////////////////
        for(var i = 0; i < spans.length; i++)
            spanStack.push(spans[i]);
    }
    return firstSpan.getLeft();
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
    return {
        x : x, 
        y : y, 
        next : null, 
        previous : null
    }
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
    function size() {
        return data.length;
    }
    return {
        peek : peek, 
        push : push, 
        pop : pop, 
        size : size
    }
}