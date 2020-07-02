util.loadStyles("/css/setting.css")
let tmpClass=$("#setting-main").attr("class")
window.onload = function(){
    $("#setting-main").attr("class",$("#setting-main").attr("class")+" show")
}
$(".settingbar-tab").click(function(){
    let c = window.params.get("dr") === 1
    if(c){
        $('#element').tooltip('enable')
    }else{
        $('#element').tooltip('disable')
    }
    hrefTo(["p","dr","sortd","sortType"],"/setting/" + $(this).attr("tab-href"))
})