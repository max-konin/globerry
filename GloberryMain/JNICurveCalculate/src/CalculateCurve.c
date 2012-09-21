/* 
 * File:   CalculateCurve.c
 * Author: signal
 *
 * Created on September 5, 2012, 5:34 PM
 */

#include "CalculateCurve.h"
#ifdef WIN32
#include <windows.h>
#else
#include <sys/mman.h>
#endif

Point* pointXY;
Point* point;
const Init init = { initCityArray, initPoint, initCity, initStack, initList };

inline float Z(float x, float y, Stack* cities) {
	float maxVal = 0, val;
	int i;
	Elem* elem = cities->stack->start;
	pointXY->lng = x;
	pointXY->lat = y;
	while(elem) {
		point->lng = ((City*)elem->data)->lng;
		point->lat = ((City*)elem->data)->lat;
		val = ((City*)elem->data)->w / distance(pointXY, point);
		if(val > maxVal)
			maxVal = val;
		elem = elem->next;
	}
	return maxVal * 1000000;
}

List* calculateCurves(Stack* cities, float zlevel) {
	float stepX = 0.6, stepY = 0.6;
	float epsilon = stepX * 17717;
	Point* curve;
	float distanceF = 500;
	City* somePoint;
	
	List* returnCurves = (List*)malloc(sizeof(List));
	init.initList(returnCurves);
	//направление выбора квадрата 0-вверх 1-вправо 2-вниз 3-влево
	int direction = -1;
	//город
	Point start;
	//массив вершин квадрата
	Point squarePoints[4];
	//переменные цикла
	int i = 0;
	//Массив куда складываются номера вершин квадрата, которые попали в срез поля
	int buffer[4];
	memset(buffer, 0, sizeof(int) * 4);
	//точка которая не попала в срез(используется для count == 3)
	int buffer2;
	//точки на квадрате по которым строится кривулина, попадают в массив returnPoints, отсчет право-лево по часовой стрелке
	Point* pointLeft;
	Point* pointRight;
	
	//начальная точка
	Point* startPoint = NULL;
	//для заполнения массива
	Point p;
	
	int exitCode = 0;
	int c = 0;
	int count = 0;
	int k = 0;
	while (cities->size != 0) {
		somePoint = (City*)cities->pop(cities);

		start.lat = floor(somePoint->lat / stepY) * stepY;
		start.lng = floor(somePoint->lng / stepX) * stepX;
				
		init.initPoint(&p, start.lat, start.lng);
		squarePoints[0] = p;
		init.initPoint(&p, start.lat, start.lng + stepX);
		squarePoints[1] = p;
		init.initPoint(&p, start.lat - stepY, start.lng + stepX);
		squarePoints[2] = p;
		init.initPoint(&p, start.lat -stepY, start.lng);
		squarePoints[3] = p;
		exitCode = 0;
		while (exitCode == 0) {
			if (c > 2000) {
				exitCode = 1;
				c = 0;
				continue;
			}
			if(c == 0)
				pointLeft = (Point*)malloc(sizeof(Point));
			pointRight = (Point*)malloc(sizeof(Point));
			count = 0;
			for (i = 0; i < 4; ++i) {
				if(Z(squarePoints[i].lng, squarePoints[i].lat, cities) > zlevel) {
					buffer[count] = i;
					++count;
				} else {
					buffer2 = i;
				}
			}
			// в зависимости от того сколько точек попало в срез поля и то какие они определяется какие точки попадут в returnPoints
			switch (count) {
				case 0:
					exitCode = -1;
					break;
				case 1:
					if (buffer[0] == 0) {
						pointRight->lat = squarePoints[0].lat - stepY / 2;
						pointRight->lng = squarePoints[0].lng;
						pointLeft->lat = squarePoints[0].lat;
						pointLeft->lng = squarePoints[0].lng + stepX / 2;
						direction = 3;
					} else if (buffer[0] == 1) {
						pointRight->lat = squarePoints[1].lat;
						pointRight->lng = squarePoints[1].lng - stepX / 2;
						pointLeft->lat = squarePoints[1].lat - stepY / 2;
						pointLeft->lng = squarePoints[1].lng;
						direction = 0;
					} else if (buffer[0] == 2) {
						pointRight->lat = squarePoints[2].lat;
						pointRight->lng = squarePoints[2].lng - stepX / 2;
						pointLeft->lat = squarePoints[2].lat + stepY / 2;
						pointLeft->lng = squarePoints[2].lng;
						direction = 1;
					} else if (buffer[0] == 3) {
						pointRight->lat = squarePoints[3].lat + stepY / 2;
						pointRight->lng = squarePoints[3].lng;
						pointLeft->lat = squarePoints[3].lat;
						pointLeft->lng = squarePoints[3].lng + stepX / 2;
						direction = 2;
					}
					break;
				case 2:
					if (buffer[0] == 0 && buffer[1] == 1) {
						pointRight->lat = squarePoints[0].lat - stepY / 2;
						pointRight->lng = squarePoints[0].lng;
						pointLeft->lat = squarePoints[1].lat - stepY / 2;
						pointLeft->lng = squarePoints[1].lng;
						direction = 3;
					} else if (buffer[0] == 1 && buffer[1] == 2) {
						pointRight->lat = squarePoints[1].lat;
						pointRight->lng = squarePoints[1].lng - stepX / 2;
						pointLeft->lat = squarePoints[2].lat;
						pointLeft->lng = squarePoints[2].lng - stepX / 2;
						direction = 0;
					} else if (buffer[0] == 2 && buffer[1] == 3) {
						pointRight->lat = squarePoints[2].lat + stepY / 2;
						pointRight->lng = squarePoints[2].lng;
						pointLeft->lat = squarePoints[3].lat + stepY / 2;
						pointLeft->lng = squarePoints[3].lng;
						direction = 1;
					} else if (buffer[0] == 0 && buffer[1] == 3) {
						pointRight->lat = squarePoints[3].lat;
						pointRight->lng = squarePoints[3].lng + stepX / 2;
						pointLeft->lat = squarePoints[0].lat;
						pointLeft->lng = squarePoints[0].lng + stepX / 2;
						direction = 2;
					} else if (buffer[0] == 0 && buffer[1] == 2) {
						if (direction == 0) {
							pointRight->lat = squarePoints[2].lat;
							pointRight->lng = squarePoints[2].lng - stepX / 2;
							pointLeft->lat = squarePoints[0].lat + stepY / 2;
							pointLeft->lng = squarePoints[0].lng;
							direction = 1;
						} else if (direction == 2) {
							pointRight->lat = squarePoints[1].lat - stepY / 2;
							pointRight->lng = squarePoints[1].lng;
							pointLeft->lat = squarePoints[3].lat;
							pointLeft->lng = squarePoints[3].lng + stepX / 2;
							direction = 3;
						}
					} else if (buffer[0] == 1 && buffer[1] == 3) {
						if (direction == 3) {
							pointRight->lat = squarePoints[1].lat;
							pointRight->lng = squarePoints[1].lng - stepX / 2;
							pointLeft->lat = squarePoints[1].lat - stepY / 2;
							pointLeft->lng = squarePoints[1].lng;
							direction = 0;
						} else if (direction == 1) {
							pointRight->lat = squarePoints[3].lat;
							pointRight->lng = squarePoints[3].lng + stepX / 2;
							pointLeft->lat = squarePoints[3].lat + stepY / 2;
							pointLeft->lng = squarePoints[3].lng;
							direction = 2;
						} else if (direction == -1) {
							direction = 2;
						}
					}
					break;
				case 3:
					if (buffer2 == 0) {
						pointRight->lat = squarePoints[0].lat;
						pointRight->lng = squarePoints[0].lng + stepX / 2;
						pointLeft->lat = squarePoints[0].lat - stepY / 2;
						pointLeft->lng = squarePoints[0].lng;
						direction = 0;
					} else if (buffer2 == 1) {
						pointRight->lat = squarePoints[1].lat - stepY / 2;
						pointRight->lng = squarePoints[1].lng;
						pointLeft->lat = squarePoints[1].lat;
						pointLeft->lng = squarePoints[1].lng - stepX / 2;
						direction = 1;
					} else if (buffer2 == 2) {
						pointRight->lat = squarePoints[2].lat;
						pointRight->lng = squarePoints[2].lng - stepX / 2;
						pointLeft->lat = squarePoints[2].lat + stepY / 2;
						pointLeft->lng = squarePoints[2].lng;
						direction = 2;
					} else if (buffer2 == 3) {
						pointRight->lat = squarePoints[3].lat + stepY / 2;
						pointRight->lng = squarePoints[3].lng;
						pointLeft->lat = squarePoints[3].lat;
						pointLeft->lng = squarePoints[3].lng + stepX / 2;
						direction = 3;
					}
					break;
				case 4:
					direction = 0;
					break;
			}
			if(startPoint != NULL)
				distanceF = distance(startPoint, pointRight);
			if (count != 4 && count != 0) {
				if (c == 0) {
					startPoint = pointLeft;
					curve = pointLeft;
				} else if (distanceF < epsilon) {
					exitCode = 1;
					curve->next = startPoint;
					continue;
				}
				curve->next = pointRight;
				curve = curve->next;
			}
			//выбор направления движения 
			switch (direction) {
				case 0:
					start.lat += stepY;
					break;
				case 1:
					start.lng += stepX;
					break;
				case 2:
					start.lat -= stepY;
					break;
				case 3:
					start.lng -= stepX;
					break;
			}
			init.initPoint(&p, start.lat, start.lng);
			squarePoints[0] = p;
			init.initPoint(&p, start.lat, start.lng + stepX);
			squarePoints[1] = p;
			init.initPoint(&p, start.lat - stepY, start.lng + stepX);
			squarePoints[2] = p;
			init.initPoint(&p, start.lat -stepY, start.lng);
			squarePoints[3] = p;
					
		}
		Elem* forCheck = cities->stack->start;
		while(forCheck != cities->stack->end) {
			if (pointInPolygon(curve, (City*)forCheck->data)) {
				forCheck->delete(forCheck, (List*)cities->stack);
			}
		}
		returnCurves->append(returnCurves, curve);
		c++;
	}
	return returnCurves;
}

const char* smootherCurve(Point* startPoint) {
	float a3, a2, a1, a0, b3, b2, b1, b0;
	Point p0, p1, p2, p3, 
			*P0 = startPoint,
			*P1 = P0->next,
			*P2 = P1->next,
			*P3 = P2->next;
	
	char *path;
	FILE *stream;
	size_t size;
	#ifdef WIN32
		stream = tmpfile();
	#else
		stream = open_memstream(&path, &size);
	#endif
	
	a3 = (-P0->lng + 3 * P1->lng - 3 * P2->lng + P3->lng) / 6;
	a2 = (P0->lng - 2 * P1->lng + P2->lng) / 2;
	a1 = (-P0->lng + P2->lng) / 2;
	a0 = (P0->lng + 4 * P1->lng + P2->lng) / 6;
	b3 = (-P0->lat + 3 * P1->lat - 3 * P2->lat + P3->lat) / 6;
	b2 = (P0->lat - 2 * P1->lat + P2->lat) / 2;
	b1 = (-P0->lat + P2->lat) / 2;
	b0 = (P0->lat + 4 * P1->lat + P2->lat) / 6;
	p0.lng = a0;
	p0.lat = b0;
	p3.lng = a3 + a2 + a1 + a0;
	p3.lat = b3 + b2 + b1 + b0;
	p1.lng = (a1 + 3 * a0)/3;
	p1.lat = (b1 + 3 * b0)/3;
	p2.lng = (2 * a1 + a2 + 3 * a0)/3;
	p2.lat = (2 * b1 + b2 + 3 * b0)/3;
	fprintf(stream, "M  %f %f C %f %f %f %f %f %f ", p0.lng, p0.lat, p1.lng, p1.lat, p2.lng, p2.lat, p3.lng, p3.lat);
	//path = "M " + p0.lng + " " + p0.lat + " ";
	//path += "C " + p1.lng + " " + p1.lat + " " + p2.lng + " " + p2.lat + " " + p3.lng + " " + p3.lat + " ";
	do {
		P0 = P0->next;
		P1 = P1->next;
		P2 = P2->next;
		P3 = P3->next;
		a3 = (-P0->lng + 3 * P1->lng - 3 * P2->lng + P3->lng) / 6;
		a2 = (P0->lng - 2 * P1->lng + P2->lng) / 2;
		a1 = (-P0->lng + P2->lng) / 2;
		a0 = (P0->lng + 4 * P1->lng + P2->lng) / 6;
		b3 = (-P0->lat + 3 * P1->lat - 3 * P2->lat + P3->lat) / 6;
		b2 = (P0->lat - 2 * P1->lat + P2->lat) / 2;
		b1 = (-P0->lat + P2->lat) / 2;
		b0 = (P0->lat + 4 * P1->lat + P2->lat) / 6;
		p0.lng = a0;
		p0.lat = b0;
		p3.lng = a3 + a2 + a1 + a0;
		p3.lat = b3 + b2 + b1 + b0;
		p1.lng = (a1 + 3 * a0)/3;
		p1.lat = (b1 + 3 * b0)/3;
		p2.lng = (2 * a1 + a2 + 3 * a0)/3;
		p2.lat = (2 * b1 + b2 + 3 * b0)/3;
		fprintf(stream, "C %f %f %f %f %f %f ", p1.lng, p1.lat, p2.lng, p2.lat, p3.lng, p3.lat);
		//path += "C " + p1.lng + " " + p1.lat + " " + p2.lng + " " + p2.lat + " " + p3.lng + " " + p3.lat + " ";
	} while(P0 != startPoint);
	fprintf(stream, "z");
	fclose(stream);
	#ifdef WIN32
		char* t = path;
		rewind(stream);
		while(*t != '\0') {
			*t = fgetc(stream);
			t++;
		}
	#else
		
	#endif	
	//path += "z";
	return path;
}

/*
 * 
 */
JNIEXPORT jobject JNICALL 
	Java_com_globerry_project_service_CurveService_calculateCurve(JNIEnv *env, jobject javaThis, jobject listCities, jfloat zlevel) {
	
	point = (Point*)malloc(sizeof(Point));
	pointXY = (Point*)malloc(sizeof(Point));
	int i;
	List cities;
	init.initList(&cities);
	City city;
	jclass cls = (*env)->GetObjectClass(env, listCities);
	if(cls == 0)
		return NULL;
	jmethodID sizeID = (*env)->GetMethodID(env, cls, "size", "()I");
	if(sizeID == 0)
		return NULL;
	jmethodID getID = (*env)->GetMethodID(env, cls, "get", "(I)Ljava/lang/Object;");
	if(getID == 0)
		return NULL;
	jint size = (*env)->CallIntMethod(env, listCities, sizeID);
	jclass cityCls = (*env)->FindClass(env, "com/globerry/project/domain/City");
	if(cityCls == 0)
		return NULL;
	jvalue jI;
	for(i = 0; i < size; i++) {		
		jfieldID longitudeID = (*env)->GetFieldID(env, cityCls, "longitude", "F");
		if(longitudeID == 0)
			return NULL;
		jfieldID latitudeID = (*env)->GetFieldID(env, cityCls, "latitude", "F");
		if(latitudeID == 0)
			return NULL;
		jfieldID weightID = (*env)->GetFieldID(env, cityCls, "weight", "F");
		if(weightID == 0)
			return NULL;
		jI.i = i;
		init.initCity(
			&city,
			(*env)->GetFloatField(env, (*env)->CallObjectMethodA(env, listCities, getID, &jI), longitudeID),
			(*env)->GetFloatField(env, (*env)->CallObjectMethodA(env, listCities, getID, &jI), latitudeID),
			(*env)->GetFloatField(env, (*env)->CallObjectMethodA(env, listCities, getID, &jI), weightID)
		);
		cities.append(
			&cities,
			&city
		);
	}
	Stack* cityArr = (Stack*)malloc(sizeof(Stack));	
	init.initStack(cityArr, &cities);
	List* curves = calculateCurves(cityArr, zlevel);
	Elem* point = curves->start;
	List smoothCurves;
	init.initList(&smoothCurves);
	const char* smoothCurve;
	do {
		smoothCurve = smootherCurve((Point*)point->data);
		smoothCurves.append(&smoothCurves, (void*)smoothCurve);
	} while(point = point->next);
	
	jclass stringCls = (*env)->FindClass(env, "java/lang/String");
	if(stringCls == 0)
		return NULL;
	jclass arrayListCls = (*env)->FindClass(env, "java/util/ArrayList");
	if(arrayListCls == 0)
		return NULL;
	jmethodID initStringID = (*env)->GetMethodID(env, stringCls, "<init>", "([C)V");
	jmethodID initListID = (*env)->GetMethodID(env, arrayListCls, "<init>", "(I)V");
	jmethodID addID = (*env)->GetMethodID(env, arrayListCls, "add", "(Ljava/lang/Object;)Z");
	if(initListID == 0 || initStringID == 0 || addID == 0)
		return NULL;
	jvalue jL;
	jL.i = smoothCurves.size;
	jobject retJavaList = (*env)->CallStaticObjectMethodA(env, arrayListCls, initListID, &jL);
	Elem* it = smoothCurves.start;
	while(it != smoothCurves.end) {
		jstring jS;
		(*env)->ReleaseStringUTFChars(env, jS, (char*)(it->data));
		jobject retJavaString = (*env)->CallStaticObjectMethod(env, stringCls, initStringID, jS);
		jvalue jA;
		jA.l = retJavaString;
		(*env)->CallBooleanMethodA(env, retJavaList, addID, &jA);
		it = it->next;
	}
	
	printf("\nHello from C!\n");
	free(point);
	free(pointXY);
	free(cityArr);
	curves->erase(curves, curves->start);
	return retJavaList;
}

