$(function () {
    $("#datepicker_shipped1").datepicker();
    $("#datepicker_shipped1").datepicker("option", "dateFormat", "M dd, yy");
    $("#datepicker_shipped2").datepicker();
    $("#datepicker_shipped2").datepicker("option", "dateFormat", "M dd, yy");
    $("#datepicker_received1").datepicker();
    $("#datepicker_received1").datepicker("option", "dateFormat", "M dd, yy");
    $("#datepicker_received2").datepicker();
    $("#datepicker_received2").datepicker("option", "dateFormat", "M dd, yy");
});

var dataObject = [
  {
    pnumber: '123-55-1',
    pname: 'pname1',
    vendor: 'some vendor',
    qty: 32,
    shipped: '23 May, 2018',
    receive: '25 May, 2018'
  },
  {
    pnumber: '123-55-2',
    pname: 'pname12',
    vendor: 'some vendor',
    qty: 15,
    shipped: '23 Apr, 2018',
    receive: '07 May, 2018'
  }];

var hotElement = document.querySelector('#hot');
var hotElementContainer = hotElement.parentNode;
var hotSettings = {
  data: dataObject,
  columns: [
    {
      data: 'pnumber',
      type: 'text',
      width: 60,
      readOnly: true
    },
    {
      data: 'pname',
      type: 'text',
      readOnly: true
    },
    {
      data: 'vendor',
      type: 'text',
      readOnly: true
    },
    {
      data: 'qty',
      type: 'numeric',
      numericFormat: {
        pattern: '0'
      },
      readOnly: true
    },
    {
      data: 'shipped',
      type: 'date',
      dateFormat: 'DD MMM, YYYY',
      correctFormat: true,
      readOnly: true
    },
    {
      data: 'recieve',
      type: 'date',
      dateFormat: 'DD MMM, YYYY',
      correctFormat: true,
      readOnly: true
    }
  ],
  stretchH: 'all',
  width: 806,
  autoWrapRow: true,
  height: 487,
  maxRows: 20,
  rowHeaders: true,
  colHeaders: [
    'PN',
    'Part number',
    'Vendor',
    'Qty',
    'Shipped',
    'Receive'
  ],
  columnSorting: true,
  sortIndicator: true
};
var hot = new Handsontable(hotElement, hotSettings);
