window.dr=parseInt(getQueryString("dr",0))
$("#btn-sidebar").click(function(){
    if($("#sidebar-wrapper").attr("class") === "bg-dark"){
        $("#btn-sidebar").attr("class","cm-btn anim-rotate")
        $("#sidebar-wrapper").attr("class","bg-dark toggled")
        $("#index-logo").attr("class","cm-navbar-brand navbar-brand")
        window.dr=1
    }else{
        $("#btn-sidebar").attr("class","cm-btn")
        $("#sidebar-wrapper").attr("class","bg-dark")
        $("#index-logo").attr("class","cm-navbar-brand navbar-brand cm-none")
        window.dr=0
    }
})
$(".navbar-tab").click(function(){
    let tClassName = $(this).find("a").attr("class")
    let tId = $(this).attr("id")
    if(tClassName.indexOf("cm-ac")===-1) {
        let c = window.dr === 1
        window.location.href = "/" + $(this).attr("route") + (window.dr === 1 ? "?dr=1" : "")
    }else {
        $(".navbar-tab").each(function (index, element) {
            if ($(this).attr("id") !== tId) {
                let item = $(this).find(".list-group-item")
                item.attr("class", item.attr("class").replace(" cm-ac", ""))
            }
        })
    }
})