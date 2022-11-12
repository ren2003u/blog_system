function checkLogin(){
    $.ajax({
        type:'get',
        url:'login',
        success:function(body){

        },
        error:function(){
            location.assign('login.html');
        }
    });
}