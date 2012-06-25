// switch the bottom on and off, and changes the state of the buttom.
var prevBotBut; //bottom buttoms
var bottomActive = false;
$(document).ready(function(){
		$(".bottomButton").click(function(){
			//animation for switching bottoms.
			if( bottomActive == false){
				if(prevBotBut != undefined){
					prevBotBut.style.background = 'rgb(37, 46, 64)';
				}
				prevBotBut = this;
				$("#bottom").animate({
					bottom:147
				},"slow");
				$("#WhiteBottom").animate({
					height:147
				},"slow", function () {
					$(("#" +prevBotBut.id + "B")).show();
				});			
				this.style.background = 'rgb(255, 122, 2)';
				$(this).animate({
					width:124
				}, 0);
				bottomActive = true;
				prevBotBut = this;
			} else
			if(bottomActive == true){
				if(prevBotBut == this){
					$("#bottom").animate({
						bottom:0
					},"slow");
					$(("#" +prevBotBut.id + "B")).hide();
					$("#WhiteBottom").animate({
						height:0
					},"slow");
					this.style.background = 'rgb(37, 46, 64)';
					$(this).animate({
						width:120
					}, 0);
					bottomActive = false;
				} else
{
					this.style.background = 'rgb(255, 122, 2)';
					$(("#" +prevBotBut.id + "B")).hide();
					$(this).animate({
						width:124
					}, 0);
					prevBotBut.style.background = 'rgb(37, 46, 64)';
					$(prevBotBut).animate({
						width:120
					}, 0);
					prevBotBut = this;
					$(("#" +prevBotBut.id + "B")).show();
				}
			}
		});
		});
