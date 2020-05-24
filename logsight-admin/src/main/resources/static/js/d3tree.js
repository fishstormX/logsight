function d3Tree(root,tag,count,depth){
    var margin = {top: 0, right: 120, bottom: 0, left: 120},
        width = (depth+1)*180+600 + margin.right,
        height = (count-1)*18+40;

    var i = 0,
        duration = 750;

    var tree = d3.layout.tree()
        .size([height, width]);

    var diagonal = d3.svg.diagonal()
        .projection(function(d) { return [d.y, d.x]; });

    var svg = d3.select(tag).append("svg")
        .attr("width", width)
        .attr("height", height<680?680:height)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


    root.x0 = height / 2;
    root.y0 = 0;
    update(root);

    // d3.json(q, function(error, flare) {
    //     if (error) throw error;
    //
    //     root = flare;
    //     root.x0 = height / 2;
    //     root.y0 = 0;
    //
    //     /* function collapse(d) {
    //          if (d.children) {
    //              d._children = d.children;
    //              d._children.forEach(collapse);
    //              d.children = null;
    //          }
    //      }
    //
    //      root.children.forEach(collapse);*/
    //     update(root);
    // });

    d3.select(self.frameElement).style("height", "800px");

    function update(source) {

        // Compute the new tree layout.
        var nodes = tree.nodes(root).reverse(),
            links = tree.links(nodes);

        // Normalize for fixed-depth.
        nodes.forEach(function(d) {

            d.y = d.depth * 180; });

        // Update the nodes…
        var node = svg.selectAll("g.node")
            .data(nodes, function(d) { return d.id || (d.id = ++i); });

        // Enter any new nodes at the parent's previous position.
        var nodeEnter = node.enter().append("g")
            .attr("class", "node")
            .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
            .on("click", click);

        nodeEnter.append("text")
            .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
            .attr("dy", ".35em")
            .attr("class", "btn")
            .attr("stroke","white")
            .attr("stroke-width","3")
            .attr("d3-id",function(d) { return d.id; })
            .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
            .text(function(d) { return d.name; })
            .style("fill-opacity", 1e-6);

        nodeEnter.append("circle")
            .attr("r", 1e-6)
            .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

        nodeEnter.append("text")
            .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
            .attr("dy", ".35em")
            .attr("class", "btn")
            .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
            .text(function(d) { return d.name; })

        // Transition nodes to their new position.
        var nodeUpdate = node.transition()
            .duration(duration)
            .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

        nodeUpdate.select("circle")
            .attr("r", 4.5)
            .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

        nodeUpdate.select("text")
            .style("fill-opacity", 1);

        // Transition exiting nodes to the parent's new position.
        var nodeExit = node.exit().transition()
            .duration(duration)
            .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
            .remove();

        nodeExit.select("circle")
            .attr("r", 1e-6);

        nodeExit.select("text")
            .style("fill-opacity", 1e-6);

        // Update the links…
        var link = svg.selectAll("path.link")
            .data(links, function(d) { return d.target.id; });

        // Enter any new links at the parent's previous position.
        link.enter().insert("path", "g")
            .attr("class", "link")
            .attr("d", function(d) {
                var o = {x: source.x0, y: source.y0};
                return diagonal({source: o, target: o});
            });

        // Transition links to their new position.
        link.transition()
            .duration(duration)
            .attr("d", diagonal);

        // Transition exiting nodes to the parent's new position.
        link.exit().transition()
            .duration(duration)
            .attr("d", function(d) {
                var o = {x: source.x, y: source.y};
                return diagonal({source: o, target: o});
            })
            .remove();

        // Stash the old positions for transition.
        nodes.forEach(function(d) {
            d.x0 = d.x;
            d.y0 = d.y;
        });
    }
    window.params.set("trx",margin.left)
    window.params.set("try",height>680?0:(680-height)/2)
    svg.attr('transform', `translate(${margin.left},${height>680?0:(680-height)/2})`)

    function dragDrag () {
        if(window.params.get("d3-start")) {
            console.log(event, d3.event.x, d3.event.y,window.params.get("d3-start"),window.params.get("d3x"),window.params.get("d3y"),window.params.get("trx"),window.params.get("try"))
            let transform = svg.attr("transform")
            let trX= window.params.get("trx") + d3.event.x - window.params.get("d3x")
            let trY= window.params.get("try") + d3.event.y - window.params.get("d3y")
            svg.attr('transform', `translate(${trX},${trY})`)
        }else{
            window.params.set("d3-start",true)
            window.params.set("dragged",true)
            window.params.set("d3x",d3.event.x)
            window.params.set("d3y",d3.event.y)
            let transform = svg.attr("transform")
            let trX = transform.substring(10,transform.indexOf(","))
            let trY = transform.substring(transform.indexOf(",")+1,transform.indexOf(")"))
            window.params.set("trx",parseFloat(trX))
            window.params.set("try",parseFloat(trY))
        }
    }
    function dragEnd () {
        window.params.set("d3-start",false)
        let transform = svg.attr("transform")
        let trX = transform.substring(10,transform.indexOf(","))
        let trY = transform.substring(transform.indexOf(",")+1,transform.indexOf(")"))
        window.params.set("trx",parseFloat(trX))
        window.params.set("try",parseFloat(trY))
    }
    svg.call(d3.behavior.drag()
        .on('dragend', dragEnd)
        .on('drag', dragDrag))

        function click(d) {
        if(window.params.get("dragged")){
            window.params.set("dragged",false)
        }else{
            if (d.children) {
                d._children = d.children;
                d.children = null;
            } else if(d._children){
                d.children = d._children;
                d._children = null;
            } else {
                if(typeof(nodeClicked) != "undefined"){
                    nodeClicked(d.id)
                }
            }
            update(d);
        }
    }
    return svg
}