day = new Date(); 
hour = day.getHours(); 
if (hour>=5 && hour<12) 
	greeting = "Доброе утро!"; 
else 
{ 
	if (hour>=12 && hour<18) 
		greeting = "Добрый день!"; 
	else 
	{ 
		if (hour>=18 && hour<24) 
			greeting = "Добрый вечер!"; 
		else 
		{ 
			if (hour>=0 && hour<5) 
				greeting = "Доброй ночи!"; 
		} 
	} 
} 
document.write(greeting);
