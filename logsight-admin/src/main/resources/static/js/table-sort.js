util.loadStyles("/css/table.css")
window.params.set("sortd",0)
window.params.set("sortType","")
let sortIcon="<svg class=\"icon\" viewBox=\"0 0 1024 1024\" xmlns=\"http://www.w3.org/2000/svg\" width=\"20px\" height=\"20px\"><path d=\"M547.5 81.1l226.1 258.4c17.1 19.6 15.2 49.4-4.4 66.5-8.6 7.5-19.6 11.7-31 11.7H285.9c-26 0-47.1-21.1-47.1-47.1 0-11.4 4.1-22.5 11.7-31l226-258.5c17.1-19.6 46.9-21.6 66.5-4.4 1.6 1.3 3.1 2.8 4.5 4.4z\" fill=\"#1296db\" p-id=\"2949\" data-spm-anchor-id=\"a313x.7781069.0.i2\" class=\"\"></path><path d=\"M547.5 942.9l226.1-258.4c17.1-19.6 15.2-49.4-4.4-66.5-8.6-7.5-19.6-11.7-31-11.7H285.9c-26 0-47.1 21.1-47.1 47.1 0 11.4 4.1 22.5 11.7 31l226.1 258.4c17.1 19.6 46.9 21.6 66.5 4.4 1.5-1.2 3-2.7 4.4-4.3z\" fill=\"#D8D8D8\"></path></svg>"
let sortIcon2="<svg class=\"icon\" style=\"transform:rotate(180deg)\" viewBox=\"0 0 1024 1024\" xmlns=\"http://www.w3.org/2000/svg\" width=\"20px\" height=\"20px\"><path d=\"M547.5 81.1l226.1 258.4c17.1 19.6 15.2 49.4-4.4 66.5-8.6 7.5-19.6 11.7-31 11.7H285.9c-26 0-47.1-21.1-47.1-47.1 0-11.4 4.1-22.5 11.7-31l226-258.5c17.1-19.6 46.9-21.6 66.5-4.4 1.6 1.3 3.1 2.8 4.5 4.4z\" fill=\"#1296db\" p-id=\"2949\" data-spm-anchor-id=\"a313x.7781069.0.i2\" class=\"\"></path><path d=\"M547.5 942.9l226.1-258.4c17.1-19.6 15.2-49.4-4.4-66.5-8.6-7.5-19.6-11.7-31-11.7H285.9c-26 0-47.1 21.1-47.1 47.1 0 11.4 4.1 22.5 11.7 31l226.1 258.4c17.1 19.6 46.9 21.6 66.5 4.4 1.5-1.2 3-2.7 4.4-4.3z\" fill=\"#D8D8D8\"></path></svg>"
let sortIcon3="<span style=\"margin-right:20px\"></span>"
function updateSortStatus(sortd,sortType){
    window.params.set("sortd",sortd)
    window.params.set("sortType",sortType)
    let field="<span style=\"margin-left: 20px\">"+$("#th-"+sortType).text()+"</span>"
    console.log(field,1)
    if(sortd===0){
        $("#th-"+sortType).html(field+sortIcon)
    }else if(sortd===1){
        $("#th-"+sortType).html(field+sortIcon2)
    }else{
        $("#th-"+sortType).html(field+sortIcon3)
    }
}
$(".sort-abled").click(function(){
        let sortd = window.params.get("sortd")
    if(window.params.get("sortType")!=$(this).attr("sort-field")){
        window.params.set("sortd",0)
    }else {
        if (sortd === 0) {
            window.params.set("sortd", 1)
        } else if (sortd === 1) {
            window.params.set("sortd", -1)
        } else {
            window.params.set("sortd", 0)
        }
    }
    window.params.set("sortType", $(this).attr("sort-field"))
    hrefTo(["p","dr","sortd","sortType"],null)
})