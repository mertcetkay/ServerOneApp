/**
 * namespace = Index
 */
var Index = {};

$(document).ready(function() {
    Index.startInitialProcess();
    Index.initEventListeners();
    Index.triggerThreadChannel();
});

Index.triggerThreadChannel = function triggerThreadChannel(){
    $.ajax({
        type: 'GET',
        url: '/init',
        success: function (response) {
            console.log("init accomplished");
        }
    });
};

Index.startInitialProcess = function startInitialProcess () {
    /*
    $.ajax({
        type: 'GET',
        url: '/init',
        dataType: 'JSON',
        success: function (response) {
            var data = response.data;
            data.each( function(index){
                $('#console').html(this);
            });
        }
    });
    */
}

/**
 * initialize button events
 */
Index.initEventListeners = function initEventListeners() {
    $("#getData").click ( function(){ Index.getDataClickEventListener(); } );
    $("#setData").click ( function(){ Index.setDataClickEventListener(); } );
}

/**
 * get-data button action
 */
Index.getDataClickEventListener = function getDataClickEventListener(){

};

/**
 * set-data button action
 */
Index.setDataClickEventListener = function setDataClickEventListener(){

    $.ajax({
        type: 'GET',
        url: '/updatexy',
        data: {
                'X' : $("#x").val() ,
                'Y' : $("#y").val()
              },
        success: function (response) {
            console.log("set val listener: " + "x: "+$("#x").val()+", y: " +$("#y").val());
        }
    });
};
