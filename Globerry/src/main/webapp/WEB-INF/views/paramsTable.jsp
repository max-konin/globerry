<%-- 
    Document   : paramsTable
    Created on : Jul 7, 2012, 5:37:37 PM
    Author     : Vadim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="panel">
    <a class="handle" href="#"><div id="divA" >Content <!-- Ссылка для пользователей с отключенным JavaScript --></div></a>
    <h3><span lang="ru">Настройка параметров</span></h3><br>
    <span lang="ru">
        <!--                    Таблица параметров-->
        <div id="paramsTable">
            <table width="300px" border="1">
                <!--        заголовок таблицы    -->
                <tr>
                    <td>Уровень приближения</td>
                    <td align="center">
                        <span id="zoom_level">1</span>
                    </td>
                </tr>
                <!--        Слайдер    -->
                <tr>
                    <td>Множитель радиуса</td>
                    <td>
                        <div class="mySliders">
                            <input type="text" id="cur-amount" style="float:left" size="2"/>
                            <div id="slider-range-min" class="paramsTableSlider" style="float:left"></div>
                        </div>
                    </td>
                </tr>
                <!--        Цвет    -->
                <tr>
                    <td>Цвет начала градиента (начало/конец)</td>
                    <td align="center">
                        <table>
                            <tr>
                                <td>
                                    <div id="gradient_start_color" class="colorSelector"><div style="background-color: #FF0000; float:left"></div></div>
                                </td>
                                <td>
                                    <div id="gradient_finish_finish" class="colorSelector"><div style="background-color: #FF0000; float:left"></div></div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <!--        Параметр    -->
                <tr>
                    <td>Прозрачность градиента (начало)</td>
                    <td>
                        <div class="mySliders">
                            <input type="text" id="gradiet_opacity_start" style="float:left" size="2"/>
                            <div id="" class="paramsTableSlider" style="float:left"></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Прозрачность градиента (конец)</td>
                    <td>
                        <div class="mySliders">
                            <input type="text" id="gradiet_opacity_finish" style="float:left" size="2"/>
                            <div id="slider-range-min" class="paramsTableSlider" style="float:left"></div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </span>
</div>