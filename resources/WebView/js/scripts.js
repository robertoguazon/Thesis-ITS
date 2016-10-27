/*Accordion*/

var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
    acc[i].onclick = function(){
        this.classList.toggle("active");
        this.nextElementSibling.classList.toggle("show");
    }
}


/* Module1-Lesson 1*/
$(document).ready(function() {
    $("#display-syntax").click(

    function() {
        $("#syntax").slideToggle('slow');
    });


    
});


/*Module1-Lesson 2*/
$(document).ready(function() {
    $("#remember").click(

    function() {
        $("#note").slideToggle('slow');
    });
    
});

/*Module2-Lesson 2*/
$(document).ready(function(){

    $('#lesson2').delay('6900').fadeIn("slow");

});

/*Module2-Lesson 6 */
$(document).ready(function() {
   
        $("#array-syntax").delay('4900').slideDown('slow');
  
    
});

/*Module3-Lesson 1*/
$(document).ready(
    function(){

    $('#fade1').delay('1900').slideDown("slow");
    $('#fade2').delay('8900').slideDown("slow");
    }
);

/*Module3-Lesson 2*/
$(document).ready(function() {
    $("#displayoperations").click(

    function() {
        $("#pemdas").slideDown('slow');
        $("#results").delay('1900').slideDown('slow');
    });
    
});


/*Module3-Lesson 3*/
$(document).ready(function() {
    $("#slideswap").click(

    function() {
        $("#swap").slideDown('slow');
    });
    
});

/*Module3-Lesson 5*/
$(document).ready(function() {
    $("#potato").click(

    function() {
        $("#potatodisplay").slideDown('slow');
    });
    
});

$(document).ready(function() {
    $("#realdeal").click(

    function() {
        $("#arraydisplay").slideDown('slow');
    });
    
});

/*Module4-Lesson1*/

$(document).ready(function(){

    $('#cries').delay('1000').slideDown("slow");

});

/*Module4-Lesson3*/

$(document).ready(function() {
    $("#booleanslide").click(

    function() {
        $("#slideitbaby").slideDown('slow');
    });
    
});