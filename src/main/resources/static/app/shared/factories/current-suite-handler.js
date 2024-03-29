(function (){
	'use strict';
	
	angular
		.module('webLog')
		.factory('SuiteHandler', SuiteHandler);
	
	SuiteHandler.$inject = ['CurrentSuite'];
	
	function SuiteHandler(CurrentSuite){
		var service = {
				getMethodsByClassId: 		getMethodsByClassId,
				containsChosen: 			containsChosen,
				clearPlatformChosen:		clearPlatformChosen,
				containsOsId: 				containsOsId,
				getOsIdByVersion: 			getOsIdByVersion,
				getVersionNameByID: 		getVersionNameByID,
				getVersionsByPlatform: 		getVersionsByPlatform,
				sortVersionsByPlatform: 	sortVersionsByPlatform,
				getDevicesByPlatform: 		getDevicesByPlatform,
				getDeviceByID: 				getDeviceByID,
				sortDevicesByOsId: 			sortDevicesByOsId,
				getDeviceIdByName: 			getDeviceIdByName,
				sortDevicesByPlatform: 		sortDevicesByPlatform,
				getBrowsers: 				getBrowsers,
				getBrowserByID: 			getBrowserByID,
				getBrowserIdByName: 		getBrowserIdByName,
				getDataFromSpecs: 			getDataFromSpecs,
				setChosenForCurrent: 		setChosenForCurrent,
				getChosen: 					getChosen,
				clearOtherChosen: 			clearOtherChosen,
				clearAllChosen: 			clearAllChosen,
				clearChosenOs: 				clearChosenOs,
				clearChosenPlatforms: 		clearChosenPlatforms,
				clearChosenDevices: 		clearChosenDevices,
				clearChosenBrowsers: 		clearChosenBrowsers,
				clearChosenMethods: 		clearChosenMethods,
				clearChosenClasses: 		clearChosenClasses,
				setUpSpecObject:			setUpSpecObject
		};
		
		/*
		 * Bad practice sollution to specs object.
		 * Specs objects gets overwritten on every chart
		 * load so this is a temporary sollution where it
		 * iterates through the former specs object
		 * and checks which are chosen, if it finds
		 * any it applies the same chosen to the new
		 * object.
		 */
		function setUpSpecObject(newSpecs){
			var i,
				j,
				currentSpecObject,
				platforms, 
				platformLength,
				platform,
				browsers, 
				browserLength,
				versions,
				versionLength,
				devices,
				deviceLength;
			
			currentSpecObject = CurrentSuite.currentSpecObject;
			
			if(currentSpecObject.length !== 0){
			
				browsers = currentSpecObject.browsers;
				browserLength = currentSpecObject.browsers.length;
				
				platformLength = currentSpecObject.platforms.length;
				
				for(i = 0; i < browserLength; i++){
					if(browsers[i].chosen === true){
						setChosenBrowserById(browsers[i].browserid, newSpecs);
					}
				}
				
				for(i = 0; i < platformLength; i++){
					if(CurrentSuite.currentSpecObject.platforms[i].chosen){
						
						platform = CurrentSuite.currentSpecObject.platforms[i];
						
						versions = platform.versions;
						versionLength = platform.versions.length;
						
						devices = platform.devices;
						deviceLength = platform.devices.length;
						
						setChosenPlatformByname(platform.osname, newSpecs);
						
						for(j = 0; j < versionLength; j++ ){
							if(versions[j].chosen === true){
								setChosenVersionById(platform.osname, versions[j].osid, newSpecs);
							}
						}
						for(j = 0; j < deviceLength; j++){
							if(devices[j].chosen === true){
								setChosenDeviceById(platform.osname, devices[j].deviceid ,newSpecs);
							}
						}
					}
				}
			}
			
			CurrentSuite.currentSpecObject = newSpecs;
			
			function setChosenBrowserById(id, newSpecs){
				var i, 
					browsers, 
					browserLength;
				browsers = newSpecs.browsers;
				browserLength = browsers.length;
				
				for(i = 0; i < browserLength; i++){
					if(browsers[i].browserid === id){
						browsers[i].chosen = true;
						return;
					}
				}
			}
			
			function setChosenPlatformByname(osname, newSpecs){
				var i, 
					platforms, 
					platformLength;
				
				
				platforms = newSpecs.platforms;
				platformLength = platforms.length;
				
				for(i = 0; i < platformLength; i++){
					if(platforms[i].osname === osname){
						platforms[i].chosen = true;
						return;
					}
				}
			}
			
			function setChosenVersionById(osname, osid, newSpecs){
				var i,
					j,
					platforms,
					platformsLength,
					versions,
					versionLength;
				
				platforms = newSpecs.platforms;
				platformLength = platforms.length;
				
				for(i = 0; i < platformLength; i++){
					if(platforms[i].osname === osname){
						versions = platforms[i].versions;
						versionLength = versions.length;
						
						for(j = 0; j < versionLength; j++){
							if (versions[j].osid === osid) {
								versions[j].chosen = true;
								return;
							}
						}
					}
				}
			}
			
			function setChosenDeviceById(osname, deviceid, newSpecs){
				var i,
					j,
					platforms,
					platformsLength,
					devices,
					deviceLength;
			
				platforms = newSpecs.platforms;
				platformLength = platforms.length;
				
				for(i = 0; i < platformLength; i++){
					if(platforms[i].osname === osname){
						devices = platforms[i].devices;
						deviceLength = devices.length;
						
						for(j = 0; j < deviceLength; j++){
							if (devices[j].deviceid === deviceid) {
								devices[j].chosen = true;
								return;
							}
						}
					}
				}
			}
		}
		
		function getMethodsByClassId(id) {
			for (var i = 0; i < CurrentSuite.currentSuite.length; i++) {
				if (CurrentSuite.currentSuite[i].id === id) {
 					return CurrentSuite.currentSuite[i].testcases;
				}
			}
		 }
		 
		function containsChosen(array){
	    	for (var i = 0; i < array.length; i++) {
				if (array[i].chosen) {
					return true;
				}
			}
	    	return false;
		}
		
	    function clearPlatformChosen(platform){
	    	
	    	var i = 0;
	    	var j = 0;
	    	
	    	var platforms = CurrentSuite.currentSpecObject.platforms;
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
	    }
	    
	    function containsOsId(osid, array){
	    	for (var i = 0; i < array.length; i++) {
				if (array[i].osid === osid) {
					return true;
				}
			}
	    	return false;
	    }
	    
		function getOsIdByVersion(osName,osVersion){
			var specs = CurrentSuite.currentSpecObject;
			for (var i = 0; i < specs.platforms.length; i++) {
				if (specs.platforms[i].osname === osName) {
					for (var j = 0; j < specs.platforms[i].versions.length; j++) {
						if (specs.platforms[i].versions[j].osver === osVersion) {
							return specs.platforms[i].versions[j].osid;
						}
					}
				}
			}
		}
		
	    function getVersionNameByID(id){
	    	for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
	    		var platform = CurrentSuite.currentSpecObject.platforms[i];
				for (var j = 0; j < platform.versions.length; j++) {
					if (platform.versions[j].osid === id) {
						return platform.osname + " " + platform.versions[j].osver;
					}
				}
			}
	    }
	    
	    function getVersionsByPlatform(platformName, chosenOS){
	    	var specs = CurrentSuite.currentSpecObject;
	    	var platformVersions = [];
	    		for (var i = 0; i < specs.platforms.length; i++) {
	    			if (specs.platforms[i].osname === platformName) {
	    				for (var j = 0; j < specs.platforms[i].versions.length; j++) {
	    					platformVersions.push(specs.platforms[i].versions[j].osid);
	    				}
	    			}
	    		}
	    	return platformVersions;
	    }
	    
	    function sortVersionsByPlatform(platformName, chosenOS){
	    	var specs = CurrentSuite.currentSpecObject;
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
	    }
	    
	    function getDevicesByPlatform(platformName, chosenDevices){
	    	var specs = CurrentSuite.currentSpecObject;
	    	var platformDevices = [];
	    		for (var i = 0; i < specs.platforms.length; i++) {
	    			if (specs.platforms[i].osname === platformName) {
	    				for (var j = 0; j < specs.platforms[i].devices.length; j++) {
	    					platformDevices.push(specs.platforms[i].devices[j].deviceid);
	    				}
	    			}
	    		}
	    	return platformDevices;
	    }
	    
	    function getDeviceByID(id){
	    	for (var i = 0; i < CurrentSuite.currentSpecObject.platforms.length; i++) {
	    		var platform = CurrentSuite.currentSpecObject.platforms[i];
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
	    }
		    
	    function sortDevicesByOsId(devices, osid){
	    	var sortedDevices = [];
	    	var platformName = '';
	    	var platforms = CurrentSuite.currentSpecObject.platforms;
	    	
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
	    }
	    
		function getDeviceIdByName(deviceName){
			var specs = CurrentSuite.currentSpecObject;
			for (var i = 0; i < specs.platforms.length; i++) {
				for (var j = 0; j < specs.platforms[i].devices.length; j++) {
					if (specs.platforms[i].devices[j].devicename === deviceName) {
						return specs.platforms[i].devices[j].deviceid;
					}
				}
			}
		}
		
	    function sortDevicesByPlatform(platformName, chosenDevices){
	    	var specs = CurrentSuite.currentSpecObject;
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
	    }
	    
	    function getBrowsers(specs, key){
	    	
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
	    }
	    
	    function getBrowserByID(id){
	    	for (var i = 0; i < CurrentSuite.currentSpecObject.browsers.length; i++) {
				if (CurrentSuite.currentSpecObject.browsers[i].browserid === id) {
					return CurrentSuite.currentSpecObject.browsers[i];
				}
			}
	    }
	    
		function getBrowserIdByName(browserName, browserVer){
			var specs = CurrentSuite.currentSpecObject;
			for (var i = 0; i < specs.browsers.length; i++) {
				if (specs.browsers[i].browsername === browserName && specs.browsers[i].browserver === browserVer) {
					return specs.browsers[i].browserid;
					
				}
			}
		}
		
		function getDataFromSpecs(base, child, key){
	    	var specs = CurrentSuite.currentSpecObject[base];
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
	    }
		
	    function setChosenForCurrent(classId, methodId){
	    	var currentSuite = CurrentSuite.currentSuite;
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
	    }
	    
	    function getChosen(){
			var platforms = CurrentSuite.currentSpecObject.platforms;
			var browsers = CurrentSuite.currentSpecObject.browsers;
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
			if (CurrentSuite.currentSuite) {
				var classes = CurrentSuite.currentSuite;
				for (i = 0; i < classes.length; i++) {
					if (classes[i].chosen) {
						chosen.classes.push(classes[i].id);
					}
				}
			}
			
			//add methods to send
			if (CurrentSuite.currentClass.testcases) {
				var testcases = CurrentSuite.currentClass.testcases;
				for (i = 0; i < testcases.length; i++) {
					if (testcases[i].chosen) {
						chosen.testcases.push(testcases[i].id);
					}
				}
			}
			return chosen;
		}
	    
		function clearOtherChosen(item){
			var classes = CurrentSuite.currentSuite;
			var methods = CurrentSuite.currentClass.testcases;
			
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
		}
		
		function clearAllChosen(){
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
		}
			
		function clearChosenOs() {
			var specs = CurrentSuite.currentSpecObject;
			if (specs.platforms) {
				for (var i = 0; i < specs.platforms.length; i++) {
					for (var j = 0; j < specs.platforms[i].versions.length; j++) {
						if (specs.platforms[i].versions[j].chosen) {
							delete specs.platforms[i].versions[j].chosen;
						}
					}
				}
			}
		}
			
		function clearChosenPlatforms(){
			var specs = CurrentSuite.currentSpecObject;
			if (specs.platforms) {
				for (var i = 0; i < specs.platforms.length; i++) {
					if (specs.platforms[i].chosen) {
						delete specs.platforms[i].chosen;
					}
				}
			}
		}
		
		function clearChosenDevices() {
			var specs = CurrentSuite.currentSpecObject;
			if (specs.platforms){
				for (var i = 0; i < specs.platforms.length; i++) {
					for (var j = 0; j < specs.platforms[i].devices.length; j++) {
						if (specs.platforms[i].devices[j].chosen) {
							delete specs.platforms[i].devices[j].chosen;
						}
					}
				}
			}
		}
		
		function clearChosenBrowsers() {
			var specs = CurrentSuite.currentSpecObject;
			if (specs.browsers) {
				for (var i = 0; i < specs.browsers.length; i++) {
					if (specs.browsers[i].chosen) {
						delete specs.browsers[i].chosen;
					}
				}
			}
		}
		
		function clearChosenMethods() {
			var currentClass = CurrentSuite.currentClass;
			if (currentClass.testcases) {
				for (var i = 0; i < currentClass.testcases.length; i++) {
					if (currentClass.testcases[i].chosen) {
						delete currentClass.testcases[i].chosen;
					}
				}
			}
		}
		
		function clearChosenClasses() {
			var currentSuite = CurrentSuite.currentSuite;
			if (currentSuite) {
				for (var i = 0; i < currentSuite.length; i++) {
					if (currentSuite[i].chosen) {
						delete currentSuite[i].chosen;
					}
				}
			}
		}
		
		return service;
	}
})();