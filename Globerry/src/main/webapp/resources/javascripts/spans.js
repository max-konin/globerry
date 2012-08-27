/**
 * Принимает на вход функцию Z от (x,y) и возвращает связный список точек, которые лежат на изолинни с уровнем zLevel, 
 * окружающие первыю точку.
 * drawCircle - это функция рисования кружочков, нужна для дебага.
 * _stepX, _stepY - вынос в глобальные параметры размеров шагов
 * polygons - массив полигонов нарисованых на карте
 */
function calculatePoints(startPoint, zLevel, Z, drawCircle, _stepX, _stepY, polygons) {
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
			stepCount = 2000;
		if(stepCount < 1)
			return null;
		while(true) {
			x = xPrev + direction*stepX;	
			if(count > stepCount/2 && Math.abs(x - _x) < eps)
				return x;
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
			x0 = resolve(x, xPrev, z, zPrev, y, eps, 50);
		} else {
			x0 = resolve(x, xPrev, z, zPrev, y, eps, 50, positionFlag);
		}
		return x0;
	}
	function findSpans(span) {
		/**
		 * Функция принимающая количество найденых на уровне спанов.
		 * isFirst - первый ли спан на уровне
		 */
		var connectSpans = function connectSpans(isFirst) {
			if(!isFirst) {
				if(direction == 1) {
					if(index == 0) {
						span.getLeft().next = newSpan.getLeft();
						newSpan.getLeft().previous = span.getLeft();
						newSpan.getRight().next = spanArrays[level][index + 1].getLeft();
						spanArrays[level][index + 1].getLeft().previous = newSpan.getRight();
					} else if(index == spanArrays[level].length - 1) {
						span.getRight().previous = newSpan.getRight();
						newSpan.getRight().next = span.getRight();
						newSpan.getLeft().previous = spanArrays[level][index - 1].getRight();
						spanArrays[level][index - 1].getRight().next = newSpan.getLeft();
					} else {
						spanArrays[level][index + 1].getLeft().previous = newSpan.getRight();
						newSpan.getRight().next = spanArrays[level][index + 1].getLeft();
						newSpan.getLeft().previous = spanArrays[level][index - 1].getRight();
						spanArrays[level][index - 1].getRight().next = newSpan.getLeft();
					}
				} else if(direction == -1) {
					if(index == 0) {
						newSpan.getLeft().next = span.getLeft();
						span.getLeft().previous = newSpan.getLeft();
						newSpan.getRight().previous = spanArrays[level][index + 1].getLeft();
						spanArrays[level][index + 1].getLeft().next = newSpan.getRight();
					} else if(index == spanArrays[level].length - 1) {
						newSpan.getRight().previous = span.getRight();
						span.getRight().next = newSpan.getRight();
						newSpan.getLeft().next = spanArrays[level][index - 1].getRight();
						spanArrays[level][index - 1].getRight().previous = newSpan.getLeft();
					} else {
						spanArrays[level][index + 1].getLeft().next = newSpan.getRight();
						newSpan.getRight().previous = spanArrays[level][index + 1].getLeft();
						newSpan.getLeft().next = spanArrays[level][index - 1].getRight();
						spanArrays[level][index - 1].getRight().previous = newSpan.getLeft();
					}
				}
			} else {
				if(direction == 1) {
					newSpan.getLeft().previous = span.getLeft();
					span.getLeft().next = newSpan.getLeft();
					newSpan.getRight().next = span.getRight();
					span.getRight().previous = newSpan.getRight();
				} else if(direction == -1) {
					newSpan.getLeft().next = span.getLeft();
					span.getLeft().previous = newSpan.getLeft();
					newSpan.getRight().previous = span.getRight();
					span.getRight().next = newSpan.getRight();
				}
			}
		},
		/**
		 * Функция которая находит или изменяет спаны из spans, новые найденые спаны заниосит в spans.
		 * Возвращает массив найденых спанов.
		 * xr - точка с которой стартует поиск.
		 * spans - рабочий массив спанов.
		 */
		findNextSpans = function findNextSpans(xr, spans) {
			var ret = [], check;
			space = findFreeSpace(xr + 2*stepX, spans);
			if(!space)
				return ret;
			xl = findBorder(xr + 2*stepX, y, 1,
					Math.min(space[1] - xr, span.getRight().x - xr + stepX)/stepX - 2, false);
			while(xl != null) {
				xr = findBorder(xl, y, 1, 
						Math.min(span.getRight().x - xl + stepX, space[1] - xl)/stepX + 3, false);
				if(xr == null) {
					var Blue = function Blue() {
						spanArrays[level][check].getLeft().x = xl;
						if(direction == 1) {
							spans[check].getLeft().previous = spans[check - 1].getLeft();
							spans[check - 1].getLeft().next = spans[check].getLeft();
						} else if(direction == -1) {		
							spans[check].getLeft().next = spans[check - 1].getLeft();
							spans[check - 1].getLeft().previous = spans[check].getLeft();
						}
						color = "blue";
					},
					Green = function Green() {
						spanArrays[level][check].getRight().x = xl;
						color = "green";
					}
					check = checkPoint(xl - 2*stepX, spans);
					// Умопомрачительные ифы, всего лишь затычка очивидная
					if(typeof check != "number") {
						check = checkPoint(xl + 2*stepX, spans);
						if(typeof check != "number") {
							check = checkPoint(space[1] + 2*stepX, spans);
							if(typeof check != "number") {
								check = checkPoint(space[0] - 2*stepX, spans);
								Green();
							} else {
								Blue();
							}
						} else {
							Blue();
						}
					} else {
						Green();
					}
					if(debug) {
						drawCircle(xl, y, color);
					}
					if(space[1] == Infinity) {
						break;
					} else {
						check = checkPoint(space[1] + 2*stepX, spans);
						space = findFreeSpace(spans[check].getRight().x + 2*stepX, spans);
						xl = findBorder(spans[check].getRight().x + 2*stepX, y, 1,
								Math.min(space[1] - xr, span.getRight().x - xr + stepX)/stepX - 2, false);
						continue;
					}
				}
				if(debug) {
					drawCircle(xl, y);
					drawCircle(xr, y);
				}
				newSpan = Span(Node(xl, y), Node(xr, y), level);
				ret.push(newSpan);
				index = insertSpan(spans, newSpan);
				newSpan.setIndex(index);
				connectSpans(false);
				space = findFreeSpace(xr + 2*stepX, spans);
				if(!space) {
					break;
				}
				xl = findBorder(xr + 2*stepX, y, 1,
						Math.min(space[1] - xr, span.getRight().x - xr + stepX)/stepX - 2, false);
			}
			return ret;
		}
		var direction = -1, level, x, y, zCurrent, space, maxStep = 1000, xl, xr, newSpan, check, tempSpace, color;
		var debug = typeof(drawCircle) == 'function', ret = [], index;
		for(var i = 0; i < 2; i++) {
			direction *= -1;
			x = span.getLeft().x;
			level = span.getLevel() + direction;
			y = span.getLeft().y + direction*stepY;
			// Проверка на условия того что это все таки рогалик, если нет, кажется ничего не ломается
			if(direction == 1 && span.getLeft().next == null && typeof spanArrays[level] != 'undefined' && typeof checkPoint(span.getRight().x, spanArrays[level]) == "number") {
				check = checkPoint(x, spanArrays[level]);
				if(typeof check == "number") {
					span.getLeft().next = spanArrays[level][check].getLeft();
					spanArrays[level][check].getLeft().previous = span.getLeft();
				} else {
					tempSpace = findFreeSpace(x, spanArrays[level]);
					if(tempSpace[1] < Infinity) {
						check = checkPoint(tempSpace[1] + 2*stepX, spanArrays[level]);
						span.getLeft().next = spanArrays[level][check].getLeft();
						spanArrays[level][check].getLeft().previous = span.getLeft();
					}
				}
			}
			check = checkPoint(x, spanArrays[level]);
			if(typeof check == "number") {
				ret = ret.concat(findNextSpans(spanArrays[level][check].getRight().x, spanArrays[level]));
				continue;
			}
			space = findFreeSpace(x, spanArrays[level]);
			zCurrent = Z(x, y);
			if(zCurrent > zLevel) {
				xl = findBorder(x, y, -1, Infinity, false);
			} else {
				var min = Math.min(span.getRight().x - span.getLeft().x, space[1] - span.getLeft().x);
				xl = findBorder(x, y, 1, min/stepX + 1, false);
				x = xl;
				if(!xl) {
					switch(direction) {
						case 1:
							span.getLeft().next = span.getRight();
							span.getRight().previous = span.getLeft();
							break;
						case -1:
							span.getRight().next = span.getLeft();
							span.getLeft().previous = span.getRight();
							break;
					}
					continue;
				}
			}
			xr = findBorder(x, y, 1, (space[1] - x)/stepX, false);
			// Ситуация когда нужно обработать не точность(нашел один край - должен быть второй или ошибка)
			if(xr == null) {
				var Blue = function Blue() {
					spanArrays[level][check].getLeft().x = xl;
					if(direction == 1) {
						spanArrays[level][check].getLeft().previous = span.getLeft();
						span.getLeft().next = spanArrays[level][check].getLeft();
					} else if(direction == -1) {		
						spanArrays[level][check].getLeft().next = span.getLeft();
						span.getLeft().previous = spanArrays[level][check].getLeft();
					}
					color = "blue";
				},
				Green = function Green() {
					spanArrays[level][check].getRight().x = xl;
					color = "green";
				}
				check = checkPoint(xl - 2*stepX, spanArrays[level]);
				// Умопомрачительные ифы, всего лишь затычка очивидная
				if(typeof check != "number") {
					check = checkPoint(xl + 2*stepX, spanArrays[level]);
					if(typeof check != "number") {
						check = checkPoint(space[1] + 2*stepX, spans);
						if(typeof check != "number") {							
							check = checkPoint(space[0] - 2*stepX, spans);
							Green();
						} else {
							Blue();
						}
					} else {					
						Blue();
					}
				} else {
					Green();
				}
				if(debug) {
					drawCircle(xl, y, color);
				}
				ret = ret.concat(findNextSpans(spanArrays[level][check].getRight().x, spanArrays[level]));
				continue;
			}
			newSpan = Span(Node(xl, y), Node(xr, y), level);
			ret.push(newSpan);
			if(debug) {
				drawCircle(xl, y);
				drawCircle(xr, y);
			}
			// Разные варианты заноса в общий список спанов
			if(!spanArrays[level]) {
				spanArrays[level] = [ newSpan ];
				newSpan.setIndex(0);
			} else {
				index = insertSpan(spanArrays[level], newSpan);
				newSpan.setIndex(index);
			}
			connectSpans(true);
			// Поиск далее на этом же уровне, если позволяет размер кривулины
			if(span.getRight().x - 2 * stepX > xr) {
				ret = ret.concat(findNextSpans(xr, spanArrays[level]));
			}
		}
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
	/**
	 * Проверяет лежит ли точка в спанах на заданном уровне.
	 */
	function checkPoint(x, spans) {
		if(!spans)
			return false;
		for(var i = 0, l = spans.length; i < l; i++) {
			if(inSpan(spans[i], x))
				return i;
		}
		return false;
	}
	function inSpan(span, x) {
		return span.getLeft().x - stepX < x && span.getRight().x + stepX > x;
	}
	function findFreeSpace(x, spans) {		
		if(!spans)
			return [-Infinity, Infinity];
		var span = spans[0];
		if(span.getLeft().x >= x) {
			if(span.getLeft().x - stepX >= x)
				return [-Infinity, span.getLeft().x - stepX];
			else return false;
		}
		span = spans.getLast();
		if(span.getRight().x <= x) {
			if(span.getRight().x + stepX <= x)
				return [span.getRight().x + stepX, Infinity];
			else return false;
		}
		for(var i = 1, l = spans.length; i < l; i++) {
			if(spans[i - 1].getRight().x + stepX > x)
				return false;
			var ls = spans[i - 1].getRight().x + stepX, rs = spans[i].getLeft().x - stepX;
			if(ls < x && rs > x)
				return [ls, rs];
		}
		return false;
	}
	function insertSpan(spans, span) {
		if(span.getLeft().x > spans.getLast().getRight().x) {
			spans.push(span);
			return spans.length - 1;
		}
		for(var sa = 0, lSA = spans.length; sa < lSA; sa++) {
			if(spans[sa].getRight().x < span.getLeft().x)
				continue;
			spans.splice(sa, 0, span);
			return sa;
		}
	}
	window.test = function test() {
		var spans1 = [];
		var y = 0;
		var span = Span(Node(0, y), Node(10, y), 0);
		spans1.push(span);
		
		span = Span(Node(15, y), Node(20, y), 0);
		console.log(insertSpan(spans1, span));
		
		span = Span(Node(-6, y), Node(-1, y), 0);
		console.log(insertSpan(spans1, span));
		
		span = Span(Node(-10, y), Node(-9, y), 0);
		console.log(insertSpan(spans1, span));
		
		span = Span(Node(12, y), Node(14, y), 0);
		console.log(insertSpan(spans1, span));
		
		spans1.forEach(function(span) {
			console.log(span.toString());
		}, spans1);
		console.log(findFreeSpace(-7, spans1));
		console.log(findFreeSpace(-0.1, spans1));
		console.log(findFreeSpace(12, spans1));
		console.log(findFreeSpace(11, spans1));
		console.log(findFreeSpace(30, spans1));
		console.log(findFreeSpace(20.1, spans1));
	}
	var eps = 0.1,
	stepX = typeof(_stepX) == 'undefined' ? 0.15 : _stepX, 
	stepY = typeof(_stepY) == 'undefined' ? 0.7 : _stepY;
    
	var p = startPoint, y = startPoint.y;
	var pLeft = findBorder(p.x, p.y, -1), pRight = findBorder(p.x, p.y, 1);
	var firstLevel = y;
	var spanArrays = [];
	var leftNode = Node(pLeft, y);
	var rightNode = Node(pRight, y);
	if(typeof(drawCircle) == 'function') {
		drawCircle(pLeft, y);
		drawCircle(pRight, y);
	}
	var spanStack = Stack();
	var firstSpan = Span(leftNode, rightNode, 0);
	spanArrays[firstSpan.getLevel()] = [firstSpan];
	spanStack.push(firstSpan);
	var count = 0;
	while(spanStack.size() > 0) {
		var span = spanStack.pop();
		var spans = findSpans(span);
		count++;
		if(count > 10000)
			break;
		for(var i = 0; i < spans.length; i++) {
			spanStack.push(spans[i]);
		}
	}
	console.log(count);
	return firstSpan.getLeft();
}
function Span(l, r , lvl) {
	var level = lvl, left = l, right = r, parent = null, index = null;
	function setParent(Parent) {
		me.parent = Parent;
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
	function setIndex(i) {
		me.index = i;
	}
	function toString() {
		return "left : [" + left.x.toFixed(2) + ", " + left.y.toFixed(2) + "], " + 
		"right : [" + right.x.toFixed(2) + ", " + right.y.toFixed(2) + "], level : " + level.toFixed(2);
	}
	var me = {
		setParent : setParent,
		setIndex: setIndex,
		getParent : getParent,
		getLevel : getLevel,
		getLeft : getLeft,
		getRight : getRight,
		getLength : getLength,
		toString : toString,
		level : lvl,
		left : left,
		right : right,
		index: index		
	};
	return me;
}
function Node(x, y) {
	return {
		x : x, 
		y : y,
		connect: false,
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