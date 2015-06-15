(function(){
	'use strict';
	
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
			timestampRaw:{},
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
		    	
		    	var i = 0;
		    	var j = 0;
		    	
		    	var platforms = this.currentSpecObject.platforms;
		    	for (i = 0; i < platforms.length; i++) {
					if (platforms[i].osname === platform.osname) {
						for (j = 0; j < platforms[i].versions.length; j++) {
							if (platforms[i].versions[j].chosen) {
								delete platforms[i].versions[j].chosen;
							}
						}
						for (j = 0; j < platforms[i].devices.length; j++) {
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
		    	var chosenVersions=[];
		    	var allVersions = [];
		    	if (chosenOS.length === 0) {
					versions = this.getVersionsByPlatform(platformName, chosenOS);
					return versions;
				} else {
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
		    	var platforms = this.currentSpecObject.platforms;
		    	
		    	var i = 0;
		    	var j = 0;
	
		    	for (i = 0; i < platforms.length; i++) {
		    		if (platforms[i].chosen) {
		    			if (this.containsOsId(osid, platforms[i].versions)) {
							for (j = 0; j < platforms[i].devices.length; j++) {
								for (var k = 0; k < devices.length; k++) {
									if (devices[k] === platforms[i].devices[j].deviceid) {
										sortedDevices.push(platforms[i].devices[j].deviceid);
									}
								}
							}
							if (sortedDevices.length === 0) {
								for (j = 0; j < platforms[i].devices.length; j++) {
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
		    	var allDevices = [];
		    	if (chosenDevices.length === 0) {
					devices = this.getDevicesByPlatform(platformName, chosenDevices);
					return devices;
				} else {
					chosenDevices=[];
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
		    	
		    	var i = 0;
		    	
		    	var browserIDs = [];
		    	if (this.containsChosen(specs)) {
			    	for (i = 0; i < specs.length; i++) {
			    		if (specs[i].chosen) {
			    			browserIDs.push(specs[i][key]);
						}
					}
		    	} else {
		    		for (i = 0; i < specs.length; i++) {
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
		    	
		    	var i = 0;
		    	var j = 0;
		    	var childLenght;
		    	var specLength;
		    	var specChild;
		    	
		    	//browsers
		    	if (base == 'browsers') {
					dataArray = this.getBrowsers(specs, key);
				} else {
				
				//the rest
					//if chosen patforms are found, only add the chosen
					if (this.containsChosen(specs)) {
						specLength = specs.length;
						for (i = 0; i < specLength; i++) {
							if (specs[i].chosen) {
					    		if (child != 'none') {
					    			//if chosen childs are found only add the chosen
					    			specChild = specs[i][child];
					    			if (this.containsChosen(specChild)) {
					    				childLength = specChild.length;
							    		for (j = 0; j < childLength; j++) {
							    			if (specChild[j].chosen) {
							    				dataArray.push(specChild[j][key]);
											}
										}
					    			} else {
					    				//if NO chosen childs found adding all
					    				childLength = specChild.length;
				    					for (j = 0; j < childLength; j++) {
				    						dataArray.push(specChild[j][key]);
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
						specLength = specs.length;
				    	for (i = 0; i < specLength; i++) {
				    		if (child != 'none') {
				    			specChild = specs[i][child];
				    			//if chosen child found adding chosen
				    			if (this.containsChosen(specChild)) {
				    				childLength = specChild.length;
						    		for (j = 0; j < childLength; j++) {
						    			if (specChild[j].chosen) {
						    				dataArray.push(specChild[j][key]);
										}
									}
				    			} else {
				    				//if NO chosen child found adding all
				    				var childLength = specChild.length;
			    					for (j = 0; j < childLength; j++) {
			    						dataArray.push(specChild[j][key]);
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
				
				var i = 0;
				var j = 0;
				
				if (platforms) {
					//add version to send
						for (i = 0; i < platforms.length; i++) {
							var versions = platforms[i].versions;
							for (j = 0; j < versions.length; j++) {
								if (versions[j].chosen) {
									chosen.os.push(versions[j].osid);
								}
							}
						}
					
					//add devices to send
						for (i = 0; i < platforms.length; i++) {
							var devices= platforms[i].devices;
							for (j = 0; j < devices.length; j++) {
								if (devices[j].chosen) {
									chosen.devices.push(devices[j].deviceid);
								}
							}
						}
					
					//add platform to send
						for (i = 0; i < platforms.length; i++) {
							if (platforms[i].chosen) {
								var osids = [];
								if (this.containsChosen(platforms[i].versions)) {
									for (j = 0; j < platforms[i].versions.length; j++) {
										if (chosen.os.indexOf(platforms[i].versions[j].osid) == -1) {
											if (platforms[i].versions[j].chosen) {
												chosen.os.push(platforms[i].versions[j].osid);
											}
										}
									}
								} else {
									for (j = 0; j < platforms[i].versions.length; j++) {
										chosen.os.push(platforms[i].versions[j].osid);
									}
								}
								chosen.platforms.push(platforms[i].osname);
							}
						}
					
					//add browsers to send
						for (i = 0; i < browsers.length; i++) {
							if (browsers[i].chosen) {
								chosen.browsers.push(browsers[i].browserid);
							}
						}
					}
				
				//add classes to send
				if (this.currentSuite) {
					var classes = this.currentSuite;
					for (i = 0; i < classes.length; i++) {
						if (classes[i].chosen) {
							chosen.classes.push(classes[i].id);
						}
					}
				}
				
				//add methods to send
				if (this.currentClass.testcases) {
					var testcases = this.currentClass.testcases;
					for (i = 0; i < testcases.length; i++) {
						if (testcases[i].chosen) {
							chosen.testcases.push(testcases[i].id);
						}
					}
				}
				return chosen;
			},
			clearOtherChosen: function(item){
				var classes = this.currentSuite;
				var methods = this.currentClass.testcases;
				
				var i = 0;
				
				//remove classes checkbox
				if (classes) {
					var suiteLength = classes.length;
					for (i = 0; i < suiteLength; i++) {
						if (classes[i].chosen && classes[i] != item) {
							delete classes[i].chosen;
						}
					}
				}
				
				//remove method checkbox
				if (methods) {
					var classLength = methods.length;
					for (i = 0; i < classLength; i++) {
						if (methods[i].chosen && methods[i] != item) {
							delete methods[i].chosen;
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
							delete currentSuite[i].chosen;
						}
					}
				}
			}
	    };
	}
})();