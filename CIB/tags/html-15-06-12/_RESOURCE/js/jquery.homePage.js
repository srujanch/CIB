// Get dynamic FeedData
function start(divName){
	$('#' + divName).append('<div id="loadingMask" style="text-align: center; margin: 15px 0 0 0;">');
	$('#loadingMask').html('Loading feeds...');
}

function finish(){
	$('#loadingMask').remove();
}

var jqxhr = $.ajax({
	type: "GET",
	dataType: 'jsonp',
	url: 'http://cibjnbuatiis01:8083/reutersFeeds/marketData.js?jsoncallback=?',
	jsonpCallback: 'parseResponse',
	crossDomain: true,
	cache: false,
	beforeSend: start('ticker'),
	complete: finish(),
	success: function(data){
	//	console.log('@data: ' + data);
		var items = [], imageHTML, infoDivSize;
		$.each(data.item, function(key, val){
			items.push('<li><span class="label">' + val.stockName + '</span> <span class="value ' + val.status + '">' + val.stockPrice + '</span></li>');
		});
		$('<ul/>', {
			'id': 'tickerContent',
			'class': 'marquee scrollableArea',
			html: items.join('')
		}).appendTo('#ticker');
	}
}).success(function(){
}).error(function(xhr, status, errorThrown){
	console.log('@Error: ' + errorThrown);
	console.log('@Status: ' + status);
	console.log('@Status Text: ' + xhr.statusText);
}).complete(function(){
});
jqxhr.complete(function(){
	(new FrontPage).init();
});
//feedscroller
window.twttr = window.twttr ||
{};
$(document).ready(function(){
	page.trendDescriptions = {};
});
window.twttr.bounds = window.twttr.bounds ||
{};
$.extend(twttr.bounds, {
	Bounds: function(a, c, b, d){
		this.x = a;
		this.y = c;
		this.width = b;
		this.height = d;
	}
});
$.extend(twttr.bounds.Bounds.prototype, {
	encloses: function(a, c){
		return a > this.x && a < this.x + this.width && c > this.y && c < this.y + this.height;
	},
	toString: function(){
		return "(" + this.x + "," + this.y + ") " + this.width + "x" + this.height;
	}
});
(function(a){
	a.fn.extend({
		bounds: function(){
			var c = this.eq(0), b = c.offset();
			return new twttr.bounds.Bounds(b.left, b.top, c.outerWidth(), c.outerHeight());
		}
	});
})(jQuery);
(function(a){
	a.extend(window, {
		TrendTip: {
			parseIntDefault: function(c, b){
				var b = b || 0, a = parseInt(c);
				return isNaN(a) ? b : a;
			},
			clearBounds: function(){
				this.data("bounds", []);
			},
			addToBounds: function(a){
				this.data("bounds") || this.data("bounds", []);
				this.data("bounds").push(a);
			},
			enclosing: function(c, b){
				this.data("bounds") || this.data("bounds", []);
				var d = !1;
				a.each(this.data("bounds"), function(a, f){
					f.encloses(c, b) && (d = !0);
				});
				return d;
			},
			clearScrollInterval: function(){
				clearInterval(this.data("interval"));
			},
			setScrollInterval: function(a){
				this.data("interval") && this.clearScrollInterval();
				this.data("interval", setInterval(a, 30));
			},
			duplicateContent: function(c){
				var b = 0;
				c.children().each(function(){
					b += a(this).outerWidth(!0);
					c.append(a(this).clone());
				});
				c.css({
					zoom: 1,
					width: 2 * b + "px"
				});
				return b;
			},
			initScroller: function(){
				var a = this, b = this.duplicateContent(a), d = TrendTip.parseIntDefault(a.css("left"), 0);
				D = function(){
					d = d % b - 1;
					a.css({
						left: d
					});
				};
				a.bind("trendEnter", function(){
					a.clearScrollInterval();
				}).bind("trendLeave", function(){
					a.setScrollInterval(D);
				}).trigger("trendLeave");
			}
		}
	});
	a.extend(a.fn, {
		trendTip: function(){
			var c = !1, b = a(this);
			a.extend(b, TrendTip);
			b.initScroller();
			b.find("li a").each(function(){
				var d = a(this).closest("li");
				a(this).mouseenter(function(c){
					var d = a(this);
					a("#ticker .inner").trigger("trendLeave");
					b.oldCapturedTrend && b.oldCapturedTrend.trigger("trendLeave");
					b.oldCapturedTrend = d;
					b.addToBounds(d.bounds());
					if (b.enclosing(c.pageX, c.pageY)) {
						a("#ticker .inner").trigger("trendEnter");
						d.trigger("trendEnter");
						var g = function(c){
							(b.enclosing(c.pageX, c.pageY)) || (a("#ticker .inner").trigger("trendLeave"), d.trigger("trendLeave"));
						};
						a(document).bind("mousemove", g);
						d.bind("trendLeave", function(){
							b.clearBounds();
							a(document).unbind("mousemove", g)
						})
					}
				}).bind("trendEnter", function(){
					if (!b.hoveringTrend) {
						var e = a(".trendtip"), f = a(this).offset(), g = Math.round(a(this).outerWidth() / 2), j = Math.round(e.outerWidth() / 2), h = d.find("a").text(), i = d.find("em.description").html();
						e.find(".trendtip-trend").html(h);
						e.find(".trendtip-trend").attr("href", a(this).attr("href")).attr("name", h);
						"" !== a.trim(i) ? (e.find(".trendtip-why").show(), e.find(".trendtip-desc").html(i)) : e.find(".trendtip-why").hide();
						c = setTimeout(function(){
							clearTimeout(c);
							d.find("a.search_link").addClass("active");
							e.css({
								top: f.top + 35,
								left: f.left + g - j,
								position: "absolute",
								zIndex: 1E4
							});
							e.fadeIn("fast", function(){
								b.hoveringTrend = !0
							});
							b.addToBounds(e.bounds())
						}, 400)
					}
				}).bind("trendLeave", function(){
					clearTimeout(c);
					a("#ticker a.search_link").removeClass("active");
					if (b.hoveringTrend) a(".trendtip").fadeOut("fast"), b.hoveringTrend = !1
				})
			});
			return this
		}
	})
})(jQuery);
function FrontPage(){
	return $.extend(this, {
		$trends: $("#ticker"),
		$trendTip: $(".trendtip:eq(0)")
	})
}

$.extend(FrontPage.prototype, {
	init: function(){
		this.initTrendHover()
	},
	initTrendHover: function(){
		this.hoveringTrend = !1;
		$("#ticker ul").trendTip()
	}
});
var page = {};
$(function(){
});
//tagsphere
(function(d){
	d.fn.tagcloud = function(n){
		var f = d.extend(d.fn.tagcloud.defaults, n);
		f.drawing_interval = 1 / (f.fps / 1E3);
		d(this).each(function(){
			d("ul", d(this)).css("display", "none");
			new y(d(this), f)
		});
		return this
	};
	d.fn.tagcloud.defaults = {
		zoom: 90,
		max_zoom: 50,
		min_zoom: 25,
		zoom_factor: 2,
		rotate_factor: -0.8,
		fps: 14,
		centrex: 150,
		centrey: 100,
		min_font_size: 11,
		max_font_size: 22,
		font_units: "px",
		random_points: 0
	};
	var y = function(n, f){
		function u(a){
			if ("#" === a.substr(0, 1)) {
				var g = 0, c = 0, d = 0;
				4 < a.length ? (g = parseInt(a.substr(1, 2), 16), c = parseInt(a.substr(3, 2), 16), d = parseInt(a.substr(5, 2), 16)) : (g = parseInt(a.substr(1, 1) + a.substr(1, 1), 16), c = parseInt(a.substr(2, 1) + a.substr(2, 1), 16), d = parseInt(a.substr(3, 1) + a.substr(3, 1), 16));
				return {
					r: g,
					g: c,
					b: d
				}
			}
			if ("rgb" === a.substr(0, 3)) return rgb = a.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/), 3 < rgb.length ? {
				r: parseInt(rgb[1]),
				g: parseInt(rgb[2]),
				b: parseInt(rgb[3])
			} : {
				r: 0,
				g: 0,
				b: 0
			};
			void 0 != window.console && console.log("unable to parse:'" + a + "' please ensure background and foreground colors for the container are set as hex values");
			return null
		}
		function B(e, g){
			for (var c in a.data) {
				var d = e * a.data[c].y + g * a.data[c].z;
				a.data[c].y = g * a.data[c].y - e * a.data[c].z;
				a.data[c].z = d
			}
		}
		function C(e, d){
			for (var c in a.data) {
				var h = -e * a.data[c].x + d * a.data[c].z;
				a.data[c].x = d * a.data[c].x + e * a.data[c].z;
				a.data[c].z = h
			}
		}
		function D(a, d){
			if (a > s) {
				var c = a - s, h = -Math.sin(c * f.rotate_factor * k), c = Math.cos(c * f.rotate_factor * k);
				C(h, c)
			}
			a < s && (c = s - a, h = Math.sin(c * f.rotate_factor * k), c = Math.cos(c * f.rotate_factor * k), C(h, c));
			d > t && (c = d - t, h = Math.sin(c * f.rotate_factor * k), c = Math.cos(c * f.rotate_factor * k), B(h, c));
			d < t && (c = t - d, h = -Math.sin(c * f.rotate_factor * k), c = Math.cos(c * f.rotate_factor * k), B(h, c));
			s = a;
			t = d;
			z = !0
		}
		function E(){
			if (z) {
				var e = 1E4, g = -1E4, c;
				for (c in a.data) {
					if (a.data[c].z < e) e = a.data[c].z;
					if (a.data[c].z > g) g = a.data[c].z
				}
				c = Math.min(e, g);
				var e = Math.max(e, g), g = e - c, h;
				for (h in a.data) {
					c = (A - p) / (a.data[h].z - p);
					var f = Math.round(100 * ((e - a.data[h].z) / g)), i, l = f;
					256 < l && (l = 256);
					0 > l && (l = 0);
					i = Math.round(o.r + l * ((v.r - o.r) / 100));
					var k = Math.round(o.g + l * ((v.g - o.g) / 100)), l = Math.round(o.b + l * ((v.b - o.b) / 100));
					i = "rgb(" + i + ", " + k + ", " + l + ")";
					d("#" + a.data[h].id + " a", j).css("color", i);
					d("#" + a.data[h].id, j).css("z-index", f);
					d("#" + a.data[h].id, j).css("left", c * a.data[h].x + q - a.data[h].cwidth);
					d("#" + a.data[h].id, j).css("top", c * a.data[h].y + w)
				}
				z = !1
			}
		}
		d(n).css("position", "relative");
		var p = -800, k = Math.PI / 180;
		Math.cos(0);
		var z = !0, j = d(n), x = "tc_" + d(n).attr("id") + "_", i = f, m = i.zoom, A, s = 0, t = 0, a = [];
		a.data = [];
		var q = f.centrex, w = f.centrey, o, v;
		o = f.background_colour ? u(f.background_colour) : u(d(n).css("background-color"));
		v = f.foreground_colour ? u(f.foreground_colour) : u(d(n).css("color"));
		a.count = d("li a", j).length;
		a.largest = 1;
		a.smallest = 0;
		d("li a", j).each(function(e){
			var g = parseInt(d(this).attr("rel"));
			0 == g && (g = 1);
			a.data[e] = {
				id: x + e,
				size: g
			};
			var c = -1 + 2 * e / (a.count - 1);
			a.data[e].theta = Math.acos(c);
			a.data[e].phi = 0 == e || e == a.count - 1 ? 0 : (a.data[e - 1].phi + 3.6 / Math.sqrt(a.count * (1 - Math.pow(c, 2)))) % (2 * Math.PI);
			a.data[e].x = Math.cos(a.data[e].phi) * Math.sin(a.data[e].theta) * (q / 2);
			a.data[e].y = Math.sin(a.data[e].phi) * Math.sin(a.data[e].theta) * (w / 2);
			a.data[e].z = Math.cos(a.data[e].theta) * (q / 2);
			if (g > a.largest) a.largest = g;
			if (g < a.smallest) a.smallest = g;
			j.append('<div id="' + x + e + '" class="point" style="position:absolute;display:none;"><a href=' + d(this).attr("href") + ">" + d(this).html() + "</a></div>")
		});
		if (0 < i.random_points) for (b = 0; b < i.random_points; b++) 
			a.count++, a.data[a.count] = {
				id: x + a.count,
				size: 1
			}, a.data[a.count].theta = 2 * Math.random() * Math.PI, a.data[a.count].phi = 2 * Math.random() * Math.PI, a.data[a.count].x = Math.cos(a.data[a.count].phi) * Math.sin(a.data[a.count].theta) * (q / 2), a.data[a.count].y = Math.sin(a.data[a.count].phi) * Math.sin(a.data[a.count].theta) * (w / 2), a.data[a.count].z = Math.cos(a.data[a.count].theta) * (q / 2), j.append('<div id="' + x + a.count + '" class="point" style="position:absolute;"><a>.</a></div>');
		var y = a.largest - a.smallest + 1, F = i.max_font_size - i.min_font_size + 1, r;
		for (r in a.data) {
			var G = parseInt(a.data[r].size / y * F) + i.min_font_size;
			d("#" + a.data[r].id, j).hasClass("background") || d("#" + a.data[r].id, j).css("font-size", G);
			a.data[r].cwidth = d("#" + a.data[r].id, j).width() / 2
		}
		d("ul", j).remove();
		A = -(m * (p - i.max_zoom) / 100) + p;
		D(q, w);
		E();
		d(".point", j).css("display", "inline");
		setInterval(E, i.drawing_interval);
		j.mousemove(function(a){
			D(a.clientX, a.clientY)
		});
		j.mousewheel(function(a, d){
			m += d * i.zoom_factor;
			if (m > i.max_zoom) m = i.max_zoom;
			if (m < i.min_zoom) m = i.min_zoom;
			A = -(m * (p - i.max_zoom) / 100) + p;
			a.preventDefault();
			return !1
		})
	}
})(jQuery);
//slidebox
function Slidebox(slideTo, autoPlay){
	var animSpeed = 2000, easeType = 'easeInOutExpo', sliderWidth = $('#slidebox').width();
	var leftPosition = $('#slidebox .container').css("left").replace("px", "");
	if (!$("#slidebox .container").is(":animated")) {
		if (slideTo == 'next') {
			if (autoPlay == 'stop') {
				clearInterval(autoPlayTimer);
			}
			if (leftPosition == -slideboxTotalContent) {
				$('#slidebox .container').animate({
					left: 0
				}, animSpeed, easeType);
				$('#slidebox .thumbs a:first-child').removeClass('thumb').addClass('selected_thumb');
				$('#slidebox .thumbs a:last-child').removeClass('selected_thumb').addClass('thumb');
			}
			else {
				$('#slidebox .container').animate({
					left: '-=' + sliderWidth
				}, animSpeed, easeType);
				$('#slidebox .thumbs .selected_thumb').next().removeClass('thumb').addClass('selected_thumb');
				$('#slidebox .thumbs .selected_thumb').prev().removeClass('selected_thumb').addClass('thumb');
			}
		}
		else if (slideTo == 'previous') {
			if (autoPlay == 'stop') {
				clearInterval(autoPlayTimer);
			}
			if (leftPosition == '0') {
				$('#slidebox .container').animate({
					left: '-' + slideboxTotalContent
				}, animSpeed, easeType);
				$('#slidebox .thumbs a:last-child').removeClass('thumb').addClass('selected_thumb');
				$('#slidebox .thumbs a:first-child').removeClass('selected_thumb').addClass('thumb');
			}
			else {
				$('#slidebox .container').animate({
					left: '+=' + sliderWidth
				}, animSpeed, easeType);
				$('#slidebox .thumbs .selected_thumb').prev().removeClass('thumb').addClass('selected_thumb');
				$('#slidebox .thumbs .selected_thumb').next().removeClass('selected_thumb').addClass('thumb');
			}
		}
		else {
			var slide2 = (slideTo - 1) * sliderWidth;
			if (leftPosition != -slide2) {
				clearInterval(autoPlayTimer);
				$('#slidebox .container').animate({
					left: -slide2
				}, animSpeed, easeType);
				$('#slidebox .thumbs .selected_thumb').removeClass('selected_thumb').addClass('thumb');
				var selThumb = $('#slidebox .thumbs a').eq((slideTo - 1));
				selThumb.removeClass('thumb').addClass('selected_thumb');
			}
		}
	}
}
(function($){
	$(document).ready(function(){
		var autoPlayTime = 8000;
		autoPlayTimer = setInterval(autoPlay, autoPlayTime);
		function autoPlay(){
			Slidebox('next');
		}
		$('#slidebox .next').click(function(){
			Slidebox('next', 'stop');
		});
		$('#slidebox .previous').click(function(){
			Slidebox('previous', 'stop');
		});
		$('#slidebox .thumbs a:first-child').removeClass('thumb').addClass('selected_thumb');
		$("#slidebox .content").each(function(i){
			slideboxTotalContent = i * $('#slidebox').width();
			$('#slidebox .container').css("width", slideboxTotalContent + $('#slidebox').width());
		});
		$('#tagContainer').tagcloud();
	});
})(jQuery);
