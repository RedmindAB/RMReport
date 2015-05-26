define({ "api": [
  {
    "type": "post",
    "url": "/admin/reportdir",
    "title": "",
    "name": "CreateReportDir",
    "group": "Admin",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n\t[\"a/nice/path\",\n\t\"another/nice/Path\"]\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "boolean",
            "optional": false,
            "field": "boolean",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/admin/reportdir/CreateReportDirWS.java",
    "groupTitle": "Admin"
  },
  {
    "type": "put",
    "url": "/admin/reportdir/",
    "title": "",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "index",
            "description": "<p>index of the report directory to change.</p> "
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n\t\"oldPath\":\"/old/path/to/reports\",\n\t\"newPath\":\"/new/path/to/reports\"\n}",
          "type": "json"
        }
      ]
    },
    "group": "Admin",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "boolean",
            "optional": false,
            "field": "return",
            "description": "<p>a boolean of success or fail.</p> "
          }
        ]
      }
    },
    "description": "<p>set the body of the request to the path that should replace the old path.</p> ",
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/admin/reportdir/UpdateReportDirWS.java",
    "groupTitle": "Admin",
    "name": "PutAdminReportdir"
  },
  {
    "type": "put",
    "url": "/admin/port/:portnum",
    "title": "",
    "name": "ScreenshotByFilename",
    "group": "Admin",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "portnum",
            "description": "<p>port number that should be set as default port.</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "boolean",
            "optional": false,
            "field": "boolean",
            "description": ""
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/admin/port/ChangePortWS.java",
    "groupTitle": "Admin"
  },
  {
    "type": "get",
    "url": "/class/getclasses",
    "title": "",
    "name": "GetClasses",
    "group": "Class",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "suiteid",
            "description": "<p>ID of the suite.</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\tid: 1,\n\t\tname: \"se.redmind.rmtest.selenium.example.GoogleExample\"\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/classes/getclasses/GetClassesWS.java",
    "groupTitle": "Class"
  },
  {
    "type": "get",
    "url": "/class/passfail",
    "title": "",
    "name": "PassFail",
    "group": "Class",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "timestamp",
            "description": "<p>timestamp of the suite.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "classid",
            "description": "<p>ID of the class.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": true,
            "field": "testcaseid",
            "description": "<p>ID of the method/testcase.</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\tpass:121,\n\tfail:4,\n\terror:0,\n\tskipped:1\n\ttotal:126\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/classes/passfail/PassFailClassWS.java",
    "groupTitle": "Class"
  },
  {
    "type": "get",
    "url": "/device/notrunforamonth",
    "title": "",
    "name": "NotRunForAMonth",
    "group": "Device",
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/device/getdevices/GetDevicesAMonthAgoWS.java",
    "groupTitle": "Device"
  },
  {
    "type": "get",
    "url": "/driver/bytestcase",
    "title": "",
    "name": "Driver_By_Testcase",
    "group": "Driver",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "id",
            "description": "<p>id of the method/testcase.</p> "
          },
          {
            "group": "Parameter",
            "type": "number",
            "optional": false,
            "field": "timestamp",
            "description": "<p>timestamp of the test run you want to get data from.</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\ttestcasename: \"testGoogle2\",\n\t\tdevicename: \"UNKNOWN\",\n\t\tosname: \"OSX\",\n\t\tosversion: \"UNKNOWN\",\n\t\tbrowsername: \"firefox\",\n\t\tbrowserversion: \"UNKNOWN\",\n\t\ttimetorun: \"8.788\",\n\t\tresult: \"passed\",\n\t\tmessage: \"{message}\"\n\t},\n\t{\n\t\ttestcasename: \"testGoogle2\",\n\t\tdevicename: \"UNKNOWN\",\n\t\tosname: \"OSX\",\n\t\tosversion: \"UNKNOWN\",\n\t\tbrowsername: \"chrome\",\n\t\tbrowserversion: \"UNKNOWN\",\n\t\ttimetorun: \"0.313\",\n\t\tresult: \"passed\",\n\t\tmessage: \"{message}\"\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/driver/GetDriverByTestcaseWS.java",
    "groupTitle": "Driver"
  },
  {
    "type": "get",
    "url": "/method/getmethods",
    "title": "",
    "name": "Get_Methods",
    "group": "Method",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "classid",
            "description": "<p>ID of the class.</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\tid: 1,\n\t\tname: \"testGoogle2\"\n\t},\n\t{\n\t\tid: 2,\n\t\tname: \"testGoogle\"\n\t},\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/method/getmethods/GetMethodsWS.java",
    "groupTitle": "Method"
  },
  {
    "type": "get",
    "url": "/screenshot/byfilename",
    "title": "",
    "name": "ScreenshotByFilename",
    "group": "Screenshot",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "timestamp",
            "description": "<p>timestamp the screenshot should be related to.</p> "
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "filename",
            "description": "<p>the name of the file.</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Image",
            "optional": false,
            "field": "image",
            "description": "<p>the image from the timestamp and filename is returned.</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/screenshot/byfilename/ScreenshotByFilenameWS.java",
    "groupTitle": "Screenshot"
  },
  {
    "type": "get",
    "url": "/screenshot/structure",
    "title": "",
    "name": "Structure",
    "group": "Screenshot",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "timestamp",
            "description": "<p>timestamp the screenshot should be related to.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "classid",
            "description": "<p>classid the screenshot should be related to.</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\tname: \"testGoogle\",\n\t\ttestcases: [\n\t\t\t{\n\t\t\t\tdevice: \"UNKNOWN\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\tbrowser: \"chrome\",\n\t\t\t\tscreenshots: [\n\t\t\t\t\t\"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle-20150313164647[OSX_UNKNOWN_UNKNOWN_chrome_UNKNOWN].png\"\n\t\t\t\t]\n\t\t\t},\n\t\t\t{\n\t\t\t\tdevice: \"UNKNOWN\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\tbrowser: \"firefox\",\n\t\t\t\tscreenshots: [\n\t\t\t\t\t\"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle-20150313164647[OSX_UNKNOWN_UNKNOWN_firefox_UNKNOWN].png\"\n\t\t\t\t]\n\t\t\t}\n\t\t]\n\t},\n\t{\n\t\tname: \"testGoogle2\",\n\t\ttestcases: [\n\t\t\t{\n\t\t\t\tdevice: \"UNKNOWN\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\tbrowser: \"chrome\",\n\t\t\t\tscreenshots: [\n\t\t\t\t\t\"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle2-20150513164647[OSX_UNKNOWN_UNKNOWN_chrome_UNKNOWN].png\"\n\t\t\t\t]\n\t\t\t},\n\t\t\t{\n\t\t\t\tdevice: \"UNKNOWN\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\tbrowser: \"firefox\",\n\t\t\t\tscreenshots: [\n\t\t\t\t\t\"se.redmind.rmtest.selenium.example.GoogleExample.testGoogle2-20150513164647[OSX_UNKNOWN_UNKNOWN_firefox_UNKNOWN].png\"\n\t\t\t\t]\n\t\t\t}\n\t\t]\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/screenshot/structure/ScreenshotStructureWS.java",
    "groupTitle": "Screenshot"
  },
  {
    "type": "post",
    "url": "/stats/graphdata",
    "title": "",
    "name": "Graph_Data",
    "group": "Stats",
    "parameter": {
      "examples": [
        {
          "title": "Request-Example:",
          "content": "[\n\t{\t\n\t\t\"name\":'Nexus One',\n\t\t\"suiteid\":1,\n\t\t\"reslimit\":50,\n\t\t\"os\":[1,2],\n\t\t\"devices\":[1,2],\n\t\t\"browsers\":[1,2],\n\t\t\"classes\":[2],\n\t\t\"testcases\":[3]\n},\n{\t\n\t\t\"name\":'iPhone 6',\n\t\t\"suiteid\":1,\n\t\t\"reslimit\":50,\n\t\t\"os\":[1,2],\n\t\t\"devices\":[1,2],\n\t\t\"browsers\":[1,2],\n\t\t\"classes\":[2],\n\t\t\"testcases\":[3]\n\t}\t\n]",
          "type": "json"
        }
      ]
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\"name\":\"Aggregation\",\n\t\"data\":[\n\t\t\t{\n\t\t\t\"timestamp\":20150513164647,\n\t\t\t\"time\":13.307,\n\t\t\t\"pass\":4,\n\t\t\t\"fail\":0,\n\t\t\t\"error\":0,\n\t\t\t\"skipped\":0\n\t\t\t},\n\t\t\t{\n\t\t\t\"timestamp\":20150513165619,\n\t\t\t\"time\":14.871,\n\t\t\t\"pass\":4,\n\t\t\t\"fail\":0,\n\t\t\t\"error\":0,\n\t\t\t\"skipped\":0\n\t\t\t},\n\t\t\t{\n\t\t\t\"timestamp\":20150516092847,\n\t\t\t\"time\":20.255000000000003,\n\t\t\t\"pass\":4,\n\t\t\t\"fail\":0,\n\t\t\t\"error\":0,\n\t\t\t\"skipped\":0\n\t\t\t}\n\t\t]\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/stats/graphdata/GetGraphDataWS.java",
    "groupTitle": "Stats"
  },
  {
    "type": "get",
    "url": "/stats/options",
    "title": "",
    "name": "Options",
    "group": "Stats",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "suiteid",
            "description": "<p>ID of the suite you want to have filter options for.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "limit",
            "description": "<p>limit the range in timestamps the options should represent</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "{\n\t\"platforms\":[\n\t\t{\n\t\t\"osname\":\"OSX\",\n\t\t\"versions\":[\n\t\t\t{\n\t\t\t\"osver\":\"UNKNOWN\",\n\t\t\t\"osid\":1\n\t\t\t}\n\t\t],\n\t\t\"devices\":[\n\t\t\t{\n\t\t\t\"devicename\":\"UNKNOWN\",\n\t\t\t\"deviceid\":1,\n\t\t\t\"osver\":\"UNKNOWN\",\n\t\t\t\"osid\":1\n\t\t\t}\n\t\t]\n\t}\n\t],\n\t\"browsers\":[\n\t\t{\n\t\t\"browsername\":\"firefox\",\n\t\t\"browserid\":2,\n\t\t\"browserver\":\"UNKNOWN\"\n\t\t},\n\t\t{\n\t\t\"browsername\":\"chrome\",\n\t\t\"browserid\":1,\n\t\t\"browserver\":\"UNKNOWN\"\n\t\t}\n\t]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/stats/grahoptions/GetGraphOptionsWS.java",
    "groupTitle": "Stats"
  },
  {
    "type": "get",
    "url": "/suite/getsuites",
    "title": "",
    "name": "GetSuites",
    "group": "Suite",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\tname: \"Sample1\",\n\t\tid: 1\n\t},\n\t{\n\t\tname: \"Sample2\",\n\t\tid: 2\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/suite/getsuites/GetSuitesWS.java",
    "groupTitle": "Suite"
  },
  {
    "type": "get",
    "url": "/suite/syso",
    "title": "",
    "name": "GetSysos",
    "group": "Suite",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "timestamp",
            "description": "<p>timestamp of the text you want to retrieve.</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "suiteid",
            "description": "<p>suite ID of the text you want to retrieve.</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "Description of driver is: OSX_UNKNOWN_UNKNOWN_chrome_UNKNOWN\nNumber of treads executing in parrallel: 2\nThis is a RemoteWebDriver Started driver: \netc... etc...",
          "type": "text"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/suite/syso/GetSuiteSysosWS.java",
    "groupTitle": "Suite"
  },
  {
    "type": "get",
    "url": "/suite/latestbyid",
    "title": "",
    "name": "Get_latest_by_id",
    "group": "Suite",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "suiteid",
            "description": "<p>ID of the suite you want to fetch</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\tid: 1,\n\t\tname: \"se.redmind.rmtest.selenium.example.GoogleExample\",\n\t\ttime: 20.255000114440918,\n\t\tresult: \"passed\",\n\t\ttestcases: [\n\t\t\t{\n\t\t\t\tid: 1,\n\t\t\t\tname: \"testGoogle2\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\ttime: 9.10099983215332\n\t\t\t},\n\t\t\t{\n\t\t\t\tid: 2,\n\t\t\t\tname: \"testGoogle\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\ttime: 11.154000282287598\n\t\t\t}\n\t\t],\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/suite/byid/GetLatestSuiteWS.java",
    "groupTitle": "Suite"
  },
  {
    "type": "get",
    "url": "/suite/bytimestamp",
    "title": "",
    "name": "Get_latest_by_timestamp",
    "group": "Suite",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "suiteid",
            "description": "<p>ID of the suite you want to fetch</p> "
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "timestamp",
            "description": "<p>ID of the suite you want to fetch</p> "
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "[\n\t{\n\t\tid: 1,\n\t\tname: \"se.redmind.rmtest.selenium.example.GoogleExample\",\n\t\ttime: 20.255000114440918,\n\t\tresult: \"passed\",\n\t\ttestcases: [\n\t\t\t{\n\t\t\t\tid: 1,\n\t\t\t\tname: \"testGoogle2\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\ttime: 9.10099983215332\n\t\t\t},\n\t\t\t{\n\t\t\t\tid: 2,\n\t\t\t\tname: \"testGoogle\",\n\t\t\t\tresult: \"passed\",\n\t\t\t\ttime: 11.154000282287598\n\t\t\t}\n\t\t],\n\t}\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./src/main/java/se/redmind/rmtest/web/route/api/suite/bytimestamp/GetSuiteByTimestampWS.java",
    "groupTitle": "Suite"
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p> "
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./src/main/resources/static/api/doc/main.js",
    "group": "_Users_gustavholfve_Documents_RMTest_RMReport_src_main_resources_static_api_doc_main_js",
    "groupTitle": "_Users_gustavholfve_Documents_RMTest_RMReport_src_main_resources_static_api_doc_main_js",
    "name": ""
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p> "
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p> "
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./target/classes/static/api/doc/main.js",
    "group": "_Users_gustavholfve_Documents_RMTest_RMReport_target_classes_static_api_doc_main_js",
    "groupTitle": "_Users_gustavholfve_Documents_RMTest_RMReport_target_classes_static_api_doc_main_js",
    "name": ""
  }
] });