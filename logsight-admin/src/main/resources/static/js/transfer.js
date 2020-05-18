/**
 * Created by liwg on 2018/1/19.
 */
(function($, window, document) {
    var transfer = function(el, options) {
        this.option = options;
        this.$el = $(el);
        this.selectData = [];
        this.unselectData = [];
        this.init();
    };
    transfer.DEFAULTS = {
        titles: ['', ''],
        search: true, ///是否显示搜索查询
        showRefresh: false, //
        clickToSelect: true,
        pagination: false, //是否支持分页
        autoHeight: false,
        url: '',
        type: "get",
        queryParams: {},
        contentType: 'application/json',
        paginationDetail: false,
        maxSelect: undefined,
        uniqueId: "", //每行的id
        dataSource: [], //默认数据源为同一个  内部会通过diffKey去区分是待选框的  还是已选框的数据，如果selectdataSource存在  则或解析为待选数据框里的数据
        selectdataSource: undefined,
        diffKey: 'flag',
        selectColumns: [],
        unselectColumns: []

    };
    transfer.prototype = {
        init: function() {
            this.initoption();
            this.initContainer();
            this.initBothTable();
            if (this.option.url) {
                this.initServer();
            } else {
                this.classifyData();
            }
            this.initEvent();
        },
        /*
         * 渲染穿梭框页面结构*/
        initContainer: function() {
            var _this = this;
            var containerHtml = ['<div class="col-sm-5 transferBox">',
                '<h5 class="unselectTitle text-center" style="margin: 0;padding: 5px 0 0 0;">' + this.unselectTitle + '<span style="margin-left: 5px;">(<span id="checkedNum1"></span><span id="unselectTotalNum"></span>)</span></h5>',
                '<table id="transferUnselectTable"></table>',
                '</div>',
                '<div class="transferBtn" style="height: 100%;width: 40px;margin: 10px;">',
                '<div class="btnList">',
                '<span class="btn btn-default forwardBtn" style="width: 40px;"><svg class="bi bi-chevron-double-right" width="16px" height="16px" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">\n' +
                '  <path fill-rule="evenodd" d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708z" clip-rule="evenodd"/>\n' +
                '  <path fill-rule="evenodd" d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708z" clip-rule="evenodd"/>\n' +
                '</svg></span>',
                '<span class="btn btn-default backwardBtn" style="width: 40px;"><svg class="bi bi-chevron-double-left" width="16px" height="16px" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">\n' +
                '  <path fill-rule="evenodd" d="M8.354 1.646a.5.5 0 0 1 0 .708L2.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" clip-rule="evenodd"/>\n' +
                '  <path fill-rule="evenodd" d="M12.354 1.646a.5.5 0 0 1 0 .708L6.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z" clip-rule="evenodd"/>\n' +
                '</svg></span>',
                '</div>',
                '</div>',
                '<div class="col-sm-5 transferBox">',
                '<h5 class="selectTitle text-center" style="margin: 0;padding: 5px 0 0 0;">' + this.selectTitle + '<span style="margin-left: 5px;">(<span id="checkedNum2"></span><span id="selectTotalNum"></span>)</span></h5>',
                '<table id="transferSelectTable"></table>',
                '</div>'
            ].join('');
            this.$el.html(containerHtml);
            this.$unselectTable = this.$el.find('#transferUnselectTable'); //待选表格
            this.$unselectTotalNum = this.$el.find('#unselectTotalNum'); //存放待选表格内总的数量
            this.$checkedNum1 = this.$el.find('#checkedNum1'); //存放待选表格中已勾选的数量
            this.$forwardBtn = this.$el.find('.forwardBtn'); //向待选表格内添加的按钮

            this.$selectTable = this.$el.find('#transferSelectTable'); //已选表格
            this.$selectTotalNum = this.$el.find('#selectTotalNum'); //存放已选表格内总的数量
            this.$checkedNum2 = this.$el.find('#checkedNum2'); //存放已选表格中已勾选的数量
            this.$backwardBtn = this.$el.find('.backwardBtn'); //向待选表格内添加的按钮
            this.option.height = this.$el.outerHeight() - this.$el.find('h5.unselectTitle').outerHeight() - 100
        },
        /*
         * 参数处理*/
        initoption: function() {
            /*
             * 两边标题参数处理*/
            if (typeof this.option.titles == 'string' || (this.option.titles instanceof Array && this.option.titles.length == 1)) {
                this.selectTitle = this.unselectTitle = this.option.titles + '';
            } else if (this.option.titles instanceof Array && this.option.titles.length > 1) {
                this.unselectTitle = this.option.titles[0];
                this.selectTitle = this.option.titles[1];
            }
            /*
             * 两个table渲染内容若一样，给任意一个columns即可*/
            if (this.option.selectColumns instanceof Array && this.option.unselectColumns instanceof Array && (!this.option.selectColumns.length && this.option.unselectColumns.length)) {
                this.option.selectColumns = JSON.parse(JSON.stringify(this.option.unselectColumns));
            } else if (this.option.selectColumns instanceof Array && this.option.unselectColumns instanceof Array && (this.option.selectColumns.length && !this.option.unselectColumns.length)) {
                this.option.unselectColumns = JSON.parse(JSON.stringify(this.option.selectColumns));
            } else if (!this.option.selectColumns instanceof Array || !this.option.unselectColumns instanceof Array) {
                return false;
            }
            /*
             * 两边table两边渲染选中的field不能一样，此处强制替换了，参数中可不写field*/
            this.option.selectColumns[0].field = this.option.diffKey + 's';
            this.option.unselectColumns[0].field = this.option.diffKey;
        },
        /*
         * 从数据中挑出已选列表和待选列表的数据*/
        classifyData: function() {
            /*
             *数据源如果为同一个则通过diffKey去区分
             *  */
            if (!this.option.dataSource) {  return false; }
            if (this.option.selectdataSource) {
                this.selectData = this.option.selectdataSource;
                this.unselectData = this.option.dataSource;
            } else {
                for (var i = 0; i < this.option.dataSource.length; i++) {
                    if (this.option.dataSource[i][this.option.diffKey]) {
                        this.selectData.push(this.option.dataSource[i]);
                    } else {
                        this.unselectData.push(this.option.dataSource[i]);
                    }
                }
            }
            this.refreshTable();
            this.showTotalNum();
        },
        /*
         * 当数据发生变化重新渲染表格*/
        refreshTable: function() {
            this.$unselectTable.bootstrapTable("load", this.unselectData);
            this.$selectTable.bootstrapTable("load", this.selectData);
        },
        /*
         * 当表格数据总量发生变化，相应改变其总数*/
        showTotalNum: function() {
            this.$unselectTotalNum.html(this.unselectData.length );
            this.$selectTotalNum.html(this.selectData.length );
        },
        /*
         * 初始化表格，开始是没有数据加入*/
        initBothTable: function() {
            var _this = this;
            this.$unselectBootstrapTable = this.$unselectTable.bootstrapTable({
                search: _this.option.search,
                showRefresh: _this.option.showRefresh,
                showToggle: false,
                showColumns: false,
                paginationDetail: _this.option.paginationDetail,
                clickToSelect: _this.option.clickToSelect,
                pagination: _this.option.pagination,
                sidePagination: 'client',
                autoHeight: false,
                height: _this.option.height,
                data: [],
                sortName: "createTime",
                sortOrder: "desc",
                uniqueId: _this.option.uniqueId,
                columns: _this.option.unselectColumns
            });
            this.$selectBootstrapTable = this.$selectTable.bootstrapTable({
                search: _this.option.search,
                showRefresh: _this.option.showRefresh,
                showToggle: false,
                paginationDetail: _this.option.paginationDetail,
                showColumns: false,
                clickToSelect: _this.option.clickToSelect,
                pagination: _this.option.pagination,
                autoHeight: false,
                height: _this.option.height,
                data: [],
                sortName: "createTime",
                sortOrder: "desc",
                uniqueId: _this.option.uniqueId,
                columns: _this.option.selectColumns
            });
            this.$selectBootstrapTable.on('check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table', function(e, rows) {
                var num = _this.$selectTable.find('tr input[name="btSelectItem"]:checked').length;
                if (num) {
                    _this.$backwardBtn.removeClass('btn-default').addClass('btn-info');
                    _this.$checkedNum2.html(num + '/');
                } else {
                    _this.$backwardBtn.removeClass('btn-info').addClass('btn-default');
                    _this.$checkedNum2.html('');
                }
            });
            this.$unselectBootstrapTable.on('check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table', function(e, rows) {
                var num = _this.$unselectTable.find('tr input[name="btSelectItem"]:checked').length;
                if (num) {
                    _this.$forwardBtn.removeClass('btn-default').addClass('btn-info');
                    _this.$checkedNum1.html(num + '/');
                } else {
                    _this.$forwardBtn.removeClass('btn-info').addClass('btn-default');
                    _this.$checkedNum1.html('');
                }
            });
        },
        /*
         * 请求数据*/
        initServer: function() {
            var _this = this;
            if (this.option.url) {
                $.ajax({
                    url: _this.option.url,
                    type: _this.option.type,
                    contentType: _this.option.contentType, //对应后台的@RequestBody
                    data: _this.option.contentType === 'application/json' && _this.option.type === 'post' ?
                        JSON.stringify(_this.option.queryParams) : _this.option.queryParams,
                    success: function(res) {
                        if (res.success) {
                            _this.option.dataSource = res.data;
                            _this.selectData = [];
                            _this.unselectData = [];
                            _this.classifyData();
                        }
                    },
                    error: function(result) {
                        console.log(11);
                    }
                });

            }
        },
        /*
         * 初始化点击事件*/
        initEvent: function() {
            var _this = this;
            this.$forwardBtn.click(function() {
                _this.transferData($(this), 1);
            });
            this.$backwardBtn.click(function() {
                _this.transferData($(this), 0);
            });
        },
        /*
         * 获取选中行的id*/
        getSelect: function($tr) {
            return $.map($tr, function(ele, index) {
                if ($(ele).find('input[name="btSelectItem"]').is(':checked')) {
                    return parseInt($(ele).attr("data-uniqueid"))
                }
            });
        },
        /*
         * 两边数据穿梭逻辑
         * @params type：穿梭方向*/
        transferData: function($dom, type) {
            var _this = this;
            if (!$dom.hasClass('btn-info')) {
                return false;
            }
            if (type) {
                var selectList = this.getSelect(this.$unselectTable.find('tbody tr'));
                if ((this.option.maxSelect - 0) && typeof(this.option.maxSelect - 0) == "number") {
                    var currenNum = selectList.length + this.selectData.length;
                    if (currenNum > this.option.maxSelect) {
                        alert(this.selectTitle + '最多只能存在' + this.option.maxSelect + '个，您选的太多了！');
                        return false;
                    }
                }
                for (var i = 0; i < this.unselectData.length; i++) {
                    if (selectList.indexOf(this.unselectData[i][this.option.uniqueId]) >= 0) {
                        this.unselectData[i][this.option.selectColumns[0].field] = false;
                        this.selectData.push(this.unselectData[i]);
                        this.unselectData.splice(i, 1);
                        i--;
                    }
                }
                this.refreshTable();
                this.$forwardBtn.removeClass('btn-info').addClass('btn-default');
                this.$checkedNum1.html('');
            } else {
                var selectList = this.getSelect(this.$selectTable.find('tbody tr'));
                for (var i = 0; i < this.selectData.length; i++) {
                    if (selectList.indexOf(this.selectData[i][this.option.uniqueId]) >= 0) {
                        this.selectData[i][this.option.unselectColumns[0].field] = false;
                        this.unselectData.push(this.selectData[i]);
                        this.selectData.splice(i, 1);
                        i--;
                    }
                }
                this.refreshTable();
                this.$backwardBtn.removeClass('btn-info').addClass('btn-default');
                this.$checkedNum2.html('');
            }
            this.showTotalNum();
        },
        /*
         * 暴露到外面的实例的方法，可返回两个表格内的数据集合
         * @params type： 必填 unselectData待选列表数据，selectData已选列表数据
         * @params arr: 非必填 若不存在直接返回源数据集合，若配置字段名则返回所需的字段集合*/
        getData: function(type, arr) {
            if (!type) { return false; }
            if (arr && typeof arr == 'string') {
                return $.map(this[type], function(item, index) {
                    return item[arr];
                });
            } else if (arr && arr instanceof Array && arr.length > 0) {
                return $.map(this[type], function(item, index) {
                    var obj = {};
                    for (var i = 0; i < arr.length; i++) {
                        obj[arr[i]] = item[arr[i]];
                    }
                    return obj;
                });
            } else {
                return this[type];
            }
        },
        /*
         * 销毁实例*/
        destroy: function() {
            this.$el.html('');
        },
        /*
         * 刷新表格  只当数据源为一个时有效*/
        refresh: function(data) {
            if (this.option.url) {
                this.initServer();
            } else {
                this.option.dataSource = JSON.parse(JSON.stringify(data));
                this.selectData = [];
                this.unselectData = [];
                this.classifyData();
            }
        },
        refreshLeft: function(data) {
            var uniqueId = this.option.uniqueId
            var selectDataId = this.getData('selectData', uniqueId);
            for (var i = 0; i < data.length; i++) {
                if (selectDataId.indexOf(data[i][uniqueId]) >= 0) {
                    data.splice(i, 1);
                    i--;
                }
            }
            this.$unselectTable.bootstrapTable("load", data);
            this.unselectData = data;
            this.showTotalNum();
        },
        refreshRight: function(data) {
            this.selectData = data;
            var uniqueId = this.option.uniqueId
            var selectDataId = this.getData('selectData', uniqueId);
            for (var i = 0; i < this.unselectData.length; i++) {
                if (selectDataId.indexOf(this.unselectData[i][uniqueId]) >= 0) {
                    this.unselectData.splice(i, 1);
                    i--;
                }
            }
            this.$selectTable.bootstrapTable("load", data);
            this.$unselectTable.bootstrapTable("load", this.unselectData);
            this.showTotalNum();

        }
    }
    var allowedMethods = ['refresh', 'refreshLeft', 'refreshRight', 'destroy', 'getData'];
    $.fn.transfer = function(option) { //向jQuery注册插件
        var e = this,
            value,
            args = Array.prototype.slice.call(arguments, 1);
        e.each(function() {
            var $this = $(this),
                data = $this.data('transfer'),
                options = $.extend({}, transfer.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);
            if (typeof option === 'string') {
                if ($.inArray(option, allowedMethods) < 0) {
                    throw new Error("Unknown method: " + option);
                }
                if (!data) {
                    return;
                }
                value = data[option].apply(data, args);

                if (option === 'destroy') {
                    $this.removeData('transfer');
                }
            }
            if (!data) {
                $this.data('transfer', (data = new transfer(this, options)));
            }
        });
        return typeof value === 'undefined' ? this : value;
    };
    $.fn.transfer.Constructor = transfer;
    $.fn.transfer.defaults = transfer.DEFAULTS;
    $.fn.transfer.methods = allowedMethods;
})(jQuery, window, document);