<%-- 
    Document   : paramsTable
    Created on : Jul 7, 2012, 5:37:37 PM
    Author     : Vadim
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="paramsTable" class="panel">
    <a class="handle" href="#"><div id="divA" >Content <!-- Ссылка для пользователей с отключенным JavaScript --></div></a>
    <h3><span lang="ru">Настройка параметров</span></h3><br>
    <span lang="ru">
        <!--                    Таблица параметров-->
        <div id="table">
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
                            <input type="text" class="cur-amount" style="float:left" size="2"/>
                            <div id="slider-range-min" class="paramsTableSlider" style="float:left"></div>
                            <div class="hiddenInputsDiv">
                                <input type="text" hidden="hidden" class="min-amount" value="15" style="float:left" size="2"/>
                                <input type="text" hidden="hidden" class="max-amount" value="150" style="float:left" size="2"/>
                            </div>
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
                                    <div id="gradient_start_color" class="colorSelector"><div style="background-color: #00FF00; float:left"></div></div>
                                </td>
                                <td>
                                    <div id="gradient_finish_color" class="colorSelector"><div style="background-color: #0000FF; float:left"></div></div>
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
                            <input type="text" id="gradiet_opacity_start" class="cur-amount" style="float:left" size="2"/>
                            <div class="paramsTableSlider" style="float:left"></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>Прозрачность градиента (конец)</td>
                    <td>
                        <div class="mySliders">
                            <input type="text" id="gradiet_opacity_finish" class="cur-amount" style="float:left" size="2"/>
                            <div  class="paramsTableSlider" style="float:left"></div>
                        </div>
                    </td>
                </tr>
                
            </table>
        </div>
    </span>
</div>