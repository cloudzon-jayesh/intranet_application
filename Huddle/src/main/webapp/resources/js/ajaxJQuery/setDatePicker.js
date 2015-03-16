var View = Backbone.View.extend({
	el : '#bdateSection',

	events : 
	{
		'mouseover #b_date' : 'createDatePicker'
	},

	createDatePicker : function(e) {
		var view = this;

		$(e.currentTarget).datepicker(
		{
			dateFormat: 'dd/mm/yy', 
			maxDate : 0,
			changeMonth: true,
            changeYear: true,
            yearRange: "-100:-18", 
			onSelect : function(dateText, datePicker) 
			{
				console.log('onSelect', dateText);
				view.selectedDate = dateText;
			}
		});
	},
	render : function() {
		this.$el.html('<input id="b_date">');
	}
});

var view = new View();

var View = Backbone.View.extend({
	el : '#JdateSection',

	events : 
	{
		'mouseover #j_date' : 'createDatePicker'
	},

	createDatePicker : function(e) {
		var view = this;

		$(e.currentTarget).datepicker(
		{
			dateFormat: 'dd/mm/yy',
			maxDate : 0,
			changeMonth: true,
            changeYear: true,
            yearRange: "-50:+-10",
			onSelect : function(dateText, datePicker) 
			{
				console.log('onSelect', dateText);
				view.selectedDate = dateText;
			}
		});
	},
	render : function() {
		this.$el.html('<input id="j_date">');
	}
});

var view = new View();

