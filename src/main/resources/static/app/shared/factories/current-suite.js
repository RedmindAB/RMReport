angular
	.module('webLog')
	.factory('CurrentSuite', currentSuite);

function currentSuite (){
	return {
    	currentSuiteInfo: [],
    	currentSuite: [],
    	currentClass: [],
    	currentClasses: [],
    	currentMethod: [],
    	allSuites: [],
    	currentMethods: [],
    	currentDriver: [],
    	currentCases: [],
    	currentDriverRuns: [],
		currentTimeStamp: '',
		currentTimeStampArray: [],
		currentSpecObject: [],
		newLine: false,
		lastRunSize: 50,
		activeQueries: [],
		getMethodsByClassId: function(id) {
			for (var i = 0; i < this.currentSuite.length; i++) {
				if (this.currentSuite[i].id === id) {
					return this.currentSuite[i].testcases;
				}
			}
		},
		containsChosen: function (array){
	    	for (var i = 0; i < array.length; i++) {
				if (array[i].chosen) {
					return true;
				}
			}
	    	return false;
	    },
	    clearPlatformChosen: function(platform){
	    	var platforms = this.currentSpecObject.platforms;
	    	for (var i = 0; i < platforms.length; i++) {
				if (platforms[i].osname === platform.osname) {
					for (var j = 0; j < platforms[i].versions.length; j++) {
						if (platforms[i].versions[j].chosen) {
							delete platforms[i].versions[j].chosen;
						}
					}
					for (var j = 0; j < platforms[i].devices.length; j++) {
						if (platforms[i].devices[j].chosen) {
							delete platforms[i].devices[j].chosen;
						}
					}
				}
			}
	    },
	    containsOsId: function(osid, array){
	    	for (var i = 0; i < array.length; i++) {
				if (array[i].osid === osid) {
					return true;
				}
			}
	    	return false;
	    },
		getOsIdByVersion: function(osName,osVersion){
			var specs = this.currentSpecObject;
			for (var i = 0; i < specs.platforms.length; i++) {
				if (specs.platforms[i].osname === osName) {
					for (var j = 0; j < specs.platforms[i].versions.length; j++) {
						if (specs.platforms[i].versions[j].osver === osVersion) {
							return specs.platforms[i].versions[j].osid;
						}
					}
				}
			}
		},
	    getVersionNameByID: function(id){
	    	for (var i = 0; i < this.currentSpecObject.platforms.length; i++) {
	    		var platform = this.currentSpecObject.platforms[i];
				for (var j = 0; j < platform.versions.length; j++) {
					if (platform.versions[j].osid === id) {
						return platform.osname + " " + platform.versions[j].osver;
					}
				}
			}
	    },
	    getVersionsByPlatform: function(platformName, chosenOS){
	    	var specs = this.currentSpecObject;
	    	var platformVersions = [];
	    		for (var i = 0; i < specs.platforms.length; i++) {
	    			if (specs.platforms[i].osname === platformName) {
	    				for (var j = 0; j < specs.platforms[i].versions.length; j++) {
	    					platformVersions.push(specs.platforms[i].versions[j].osid);
	    				}
	    			}
	    		}
	    	return platformVersions;
	    },
	    sortVersionsByPlatform: function(platformName, chosenOS){
	    	var specs = this.currentSpecObject;
	    	var versions = [];
	    	if (chosenOS.length === 0) {
				versions = this.getVersionsByPlatform(platformName, chosenOS);
				return versions;
			} else {
				var allVersions = [];
				var chosenVersions=[];
				for (var i = 0; i < specs.platforms.length; i++) {
					if (specs.platforms[i].osname === platformName) {
						for (var j = 0; j < specs.platforms[i].versions.length; j++) {
							allVersions.push(specs.platforms[i].versions[j].osid);
							if (specs.platforms[i].versions[j].chosen) {
								chosenVersions.push(specs.platforms[i].versions[j].osid);
							}
						}
					}
				}
			}
	    	if (chosenVersions.length === 0) {
				return allVersions;
			} else {
				return chosenVersions;
			}
	    },
	    getDevicesByPlatform: function(platformName, chosenDevices){
	    	var specs = this.currentSpecObject;
	    	var platformDevices = [];
	    		for (var i = 0; i < specs.platforms.length; i++) {
	    			if (specs.platforms[i].osname === platformName) {
	    				for (var j = 0; j < specs.platforms[i].devices.length; j++) {
	    					platformDevices.push(specs.platforms[i].devices[j].deviceid);
	    				}
	    			}
	    		}
	    	return platformDevices;
	    },
	    getDeviceByID: function(id){
	    	for (var i = 0; i < this.currentSpecObject.platforms.length; i++) {
	    		var platform = this.currentSpecObject.platforms[i];
				for (var j = 0; j < platform.devices.length; j++) {
					if (platform.devices[j].deviceid === id) {
						if (platform.devices[j].devicename === "UNKNOWN") {
							return "Others";
						} else {
							return platform.devices[j].devicename;
						}
					}
				}
			}
	    },
	    sortDevicesByOsId: function(devices, osid){
	    	var sortedDevices = [];
	    	var platformName = '';
	    	var platforms = this.currentSpecObject.platforms

	    	for (var i = 0; i < platforms.length; i++) {
	    		if (platforms[i].chosen) {
	    			if (this.containsOsId(osid, platforms[i].versions)) {
						for (var j = 0; j < platforms[i].devices.length; j++) {
							for (var k = 0; k < devices.length; k++) {
								if (devices[k] === platforms[i].devices[j].deviceid) {
									sortedDevices.push(platforms[i].devices[j].deviceid);
								}
							}
						}
						if (sortedDevices.length === 0) {
							for (var j = 0; j < platforms[i].devices.length; j++) {
								sortedDevices.push(platforms[i].devices[j].deviceid);
							}
						}
	    			}
	    		}
			}
	    	return sortedDevices;
	    },
		getDeviceIdByName: function(deviceName){
			var specs = this.currentSpecObject;
			for (var i = 0; i < specs.platforms.length; i++) {
				for (var j = 0; j < specs.platforms[i].devices.length; j++) {
					if (specs.platforms[i].devices[j].devicename === deviceName) {
						return specs.platforms[i].devices[j].deviceid;
					}
				}
			}
		},
	    sortDevicesByPlatform: function(platformName, chosenDevices){
	    	var specs = this.currentSpecObject;
	    	var devices = [];
	    	if (chosenDevices.length === 0) {
				devices = this.getDevicesByPlatform(platformName, chosenDevices);
				return devices;
			} else {
				var allDevices = [];
				var chosenDevices=[];
				for (var i = 0; i < specs.platforms.length; i++) {
					if (specs.platforms[i].osname === platformName) {
						for (var j = 0; j < specs.platforms[i].devices.length; j++) {
							allDevices.push(specs.platforms[i].devices[j].deviceid);
							if (specs.platforms[i].devices[j].chosen) {
								chosenDevices.push(specs.platforms[i].devices[j].deviceid);
							}
						}
					}
				}
			}
	    	if (chosenDevices.length === 0) {
				return allDevices;
			} else {
				return chosenDevices;
			}
	    },
	    getBrowsers: function(specs, key){
	    	browserIDs = [];
	    	if (this.containsChosen(specs)) {
		    	for (var i = 0; i < specs.length; i++) {
		    		if (specs[i].chosen) {
		    			browserIDs.push(specs[i][key]);
					}
				}
	    	} else {
	    		for (var i = 0; i < specs.length; i++) {
					browserIDs.push(specs[i][key]);
				}
	    	}
	    	return browserIDs;
	    },
	    getBrowserByID: function(id){
	    	for (var i = 0; i < this.currentSpecObject.browsers.length; i++) {
				if (this.currentSpecObject.browsers[i].browserid === id) {
					return this.currentSpecObject.browsers[i];
				}
			}
	    },
		getBrowserIdByname: function (browserName, browserVer){
			var specs = this.currentSpecObject;
			for (var i = 0; i < specs.browsers.length; i++) {
				if (specs.browsers[i].browsername === browserName && specs.browsers[i].browserver === browserVer) {
					return specs.browsers[i].browserid;
					
				}
			}
		},
		getDataFromSpecs: function (base, child, key){
	    	var specs = this.currentSpecObject[base];
	    	var dataArray = [];
	    	var chosenBaseArray = [];
	    	
	    	//browsers
	    	if (base == 'browsers') {
				dataArray = this.getBrowsers(specs, key);
			} else {
			
			//the rest
				//if chosen patforms are found, only add the chosen
				if (this.containsChosen(specs)) {
					for (var i = 0; i < specs.length; i++) {
						if (specs[i].chosen) {
				    		if (child != 'none') {
				    			//if chosen childs are found only add the chosen
				    			if (this.containsChosen(specs[i][child])) {
						    		for (var j = 0; j < specs[i][child].length; j++) {
						    			if (specs[i][child][j].chosen) {
						    				dataArray.push(specs[i][child][j][key]);
										}
									}
				    			} else {
				    				//if NO chosen childs found adding all
			    					for (var j = 0; j < specs[i][child].length; j++) {
			    						dataArray.push(specs[i][child][j][key]);
			    					}
				    			}
				    			//if no child is specified adding osName
				    		} else {
				    			dataArray.push(specs[i][key]);
				    		}
						}
					}
					//if NO chosen platforms are found adding all
				} else {
			    	for (var i = 0; i < specs.length; i++) {
			    		if (child != 'none') {
			    			//if chosen child found adding chosen
			    			if (this.containsChosen(specs[i][child])) {
					    		for (var j = 0; j < specs[i][child].length; j++) {
					    			if (specs[i][child][j].chosen) {
					    				dataArray.push(specs[i][child][j][key]);
									}
								}
			    			} else {
			    				//if NO chosen child found adding all
		    					for (var j = 0; j < specs[i][child].length; j++) {
		    						dataArray.push(specs[i][child][j][key]);
		    					}
			    			}
			    			//if no child is specified adding osName
			    		} else {
			    			dataArray.push(specs[i][key]);
			    		}
					}
				}
			}
		    return dataArray;
	    },
	    setChosenForCurrent: function(classId, methodId){
	    	var currentSuite = this.currentSuite;
	    	for (var i = 0; i < currentSuite.length; i++) {
				if (currentSuite[i].id === classId) {
					if (methodId) {
						for (var j = 0; j < currentSuite[i].testcases.length; j++) {
							if (currentSuite[i].testcases[j].id === methodId) {
								currentSuite[i].testcases[j].chosen = true;
								break;
							}
						}
					}
					currentSuite[i].chosen = true;
					break;
				}
	    	}
	    },
	    getChosen: function(){
			var platforms = this.currentSpecObject.platforms;
			var browsers = this.currentSpecObject.browsers;
			var chosen = {
					os: [],
					devices: [],
					browsers: [],
					classes: [],
					testcases: [],
					platforms:[]
			};
			if (platforms) {
				//add version to send
					for (var i = 0; i < platforms.length; i++) {
						var versions = platforms[i].versions;
						for (var j = 0; j < versions.length; j++) {
							if (versions[j].chosen) {
								chosen.os.push(versions[j].osid);
							}
						}
					}
				
				//add devices to send
					for (var i = 0; i < platforms.length; i++) {
						var devices= platforms[i].devices;
						for (var j = 0; j < devices.length; j++) {
							if (devices[j].chosen) {
								chosen.devices.push(devices[j].deviceid);
							}
						}
					}
				
				//add platform to send
					for (var i = 0; i < platforms.length; i++) {
						if (platforms[i].chosen) {
							var osids = [];
							if (this.containsChosen(platforms[i].versions)) {
								for (var j = 0; j < platforms[i].versions.length; j++) {
									if (chosen.os.indexOf(platforms[i].versions[j].osid) == -1) {
										if (platforms[i].versions[j].chosen) {
											chosen.os.push(platforms[i].versions[j].osid);
										}
									}
								}
							} else {
								for (var j = 0; j < platforms[i].versions.length; j++) {
									chosen.os.push(platforms[i].versions[j].osid);
								}
							}
							chosen.platforms.push(platforms[i].osname);
						}
					}
				
				//add browsers to send
					for (var i = 0; i < browsers.length; i++) {
						if (browsers[i].chosen) {
							chosen.browsers.push(browsers[i].browserid);
						}
					}
				}
			
			//add classes to send
			if (this.currentSuite) {
				var classes = this.currentSuite;
				for (var i = 0; i < classes.length; i++) {
					if (classes[i].chosen) {
						chosen.classes.push(classes[i].id);
					}
				}
			}
			
			//add methods to send
			if (this.currentClass.testcases) {
				var testcases = this.currentClass.testcases
				for (var i = 0; i < testcases.length; i++) {
					if (testcases[i].chosen) {
						chosen.testcases.push(testcases[i].id);
					}
				}
			}
			return chosen;
		},
		clearOtherChosen: function(item){
			//remove classes checkbox
			var currentSuite = this.currentSuite;
			
			if (currentSuite) {
				for (var i = 0; i < currentSuite.length; i++) {
					if (currentSuite[i].chosen && currentSuite[i] != item) {
						delete currentSuite[i].chosen;
					}
				}
			}
			
			var currentClass = this.currentClass;
			//remove method checkbox
			if (currentClass.testcases) {
				for (var i = 0; i < currentClass.testcases.length; i++) {
					if (currentClass.testcases[i].chosen && currentClass.testcases[i] != item) {
						delete currentClass.testcases[i].chosen;
					}
				}
			}
			
			var currentDrivers = this.currentDrivers;
			//remove driver checkbox
			if (currentDrivers) {
				for (var i = 0; i < currentDrivers.length; i++) {
					if (currentDrivers[i].chosen && currentDrivers[i] != item) {
						delete currentDrivers[i].chosen;
					}
				}
			}
		},
		clearAllChosen: function(){
			//remove classes checkbox
			this.clearChosenClasses();
			
			//remove method checkbox
			this.clearChosenMethods();
			
			//remove browser checkbox
			this.clearChosenBrowsers();
			
			//remove device checkbox
			this.clearChosenDevices();
			
			//remove os checkbox
			this.clearChosenOs();
			
			//remove platform checkbox
			this.clearChosenPlatforms();
		},
		clearChosenOs: function() {
			var specs = this.currentSpecObject;
			if (specs.platforms) {
				for (var i = 0; i < specs.platforms.length; i++) {
					for (var j = 0; j < specs.platforms[i].versions.length; j++) {
						if (specs.platforms[i].versions[j].chosen) {
							delete specs.platforms[i].versions[j].chosen;
						}
					}
				}
			}
		},
		clearChosenPlatforms: function(){
			var specs = this.currentSpecObject;
			if (specs.platforms) {
				for (var i = 0; i < specs.platforms.length; i++) {
					if (specs.platforms[i].chosen) {
						delete specs.platforms[i].chosen;
					}
				}
			}
		},
		clearChosenDevices: function() {
			var specs = this.currentSpecObject;
			if (specs.platforms){
				for (var i = 0; i < specs.platforms.length; i++) {
					for (var j = 0; j < specs.platforms[i].devices.length; j++) {
						if (specs.platforms[i].devices[j].chosen) {
							delete specs.platforms[i].devices[j].chosen;
						}
					}
				}
			}
		},
		clearChosenBrowsers: function() {
			var specs = this.currentSpecObject;
			if (specs.browsers) {
				for (var i = 0; i < specs.browsers.length; i++) {
					if (specs.browsers[i].chosen) {
						delete specs.browsers[i].chosen;
					}
				}
			}
		},
		clearChosenMethods: function() {
			var currentClass = this.currentClass;
			if (currentClass.testcases) {
				for (var i = 0; i < currentClass.testcases.length; i++) {
					if (currentClass.testcases[i].chosen) {
						delete currentClass.testcases[i].chosen;
					}
				}
			}
		},
		clearChosenClasses: function() {
			var currentSuite = this.currentSuite;
			if (currentSuite) {
				for (var i = 0; i < currentSuite.length; i++) {
					if (currentSuite[i].chosen) {
						delete currentSuite[i].chosen
					}
				}
			}
		}
    };
};