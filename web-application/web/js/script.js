var App = function () {
    var e = "", t = !1, a = !1, i = !1, o = !1, n = [], r = function () {
		for (var e in n) {
            var t = n[e];
            t.call()
        }
    }
	
	, s = function () {
        var e = window, t = "inner";
        return"innerWidth"in window || (t = "client", e = document.documentElement || document.body), {width: e[t + "Width"], height: e[t + "Height"]}
    }
	
	, l = function () {
        i = $("#sidebar").hasClass("mini-menu"), o = $("#header").hasClass("navbar-fixed-top")
    }
	
	, c = function () {
        var e, t = $("#content"), a = $("#sidebar"), i = $("body");
        e = i.hasClass("sidebar-fixed") ? $(window).height() - $("#header").height() + 1 : a.height() + 20, e >= t.height() && t.attr("style", "min-height:" + e + "px !important")
    }
	
	, d = function () {
        jQuery(".sidebar-menu .has-sub > a").click(function () {
			var e = jQuery(".has-sub.open", $(".sidebar-menu"));
            e.removeClass("open"), jQuery(".arrow", e).removeClass("open"), jQuery(".sub", e).slideUp(200);
            var t = $(this), a = -200, i = 200, o = jQuery(this).next();
            o.is(":visible") ? (jQuery(".arrow", jQuery(this)).removeClass("open"), jQuery(this).parent().removeClass("open"), o.slideUp(i, function () {
                0 == $("#sidebar").hasClass("sidebar-fixed") && App.scrollTo(t, a), c()
            })) : (jQuery(".arrow", jQuery(this)).addClass("open"), jQuery(this).parent().addClass("open"), o.slideDown(i, function () {
                0 == $("#sidebar").hasClass("sidebar-fixed") && App.scrollTo(t, a), c()
            }))
        }), jQuery(".sidebar-menu .has-sub .sub .has-sub-sub > a").click(function () {
            var e = jQuery(".has-sub-sub.open", $(".sidebar-menu"));
            e.removeClass("open"), jQuery(".arrow", e).removeClass("open"), jQuery(".sub", e).slideUp(200);
            var t = jQuery(this).next();
            t.is(":visible") ? (jQuery(".arrow", jQuery(this)).removeClass("open"), jQuery(this).parent().removeClass("open"), t.slideUp(200)) : (jQuery(".arrow", jQuery(this)).addClass("open"), jQuery(this).parent().addClass("open"), t.slideDown(200))
        })
    }
	
	, m = function () {
        var e = document.getElementById("sidebar-collapse").querySelector('[class*="fa-"]'), a = e.getAttribute("data-icon1"), i = e.getAttribute("data-icon2");
        jQuery(".navbar-brand").addClass("mini-menu"), jQuery("#sidebar").addClass("mini-menu"), jQuery("#main-content").addClass("margin-left-50"), jQuery(".sidebar-collapse i").removeClass(a), jQuery(".sidebar-collapse i").addClass(i), jQuery(".search").attr("placeholder", ""), t = !0, $.cookie("mini_sidebar", "1")
    }
	
	, u = function () {
        var e = $(window).width();
        if (768 > e)
            a = !0, jQuery("#main-content").addClass("margin-left-0");
        else {
            a = !1, jQuery("#main-content").removeClass("margin-left-0");
            var t = $(".sidebar");
            1 === t.parent(".slimScrollDiv").size() && (t.slimScroll({destroy: !0}), t.removeAttr("style"), $("#sidebar").removeAttr("style"))
        }
    }
	
	, h = function () {
        s();
        "1" === $.cookie("mini_sidebar") && (jQuery(".navbar-brand").addClass("mini-menu"), jQuery("#sidebar").addClass("mini-menu"), jQuery("#main-content").addClass("margin-left-50"), t = !0), jQuery(".sidebar-collapse").click(function () {
            if (a && !i)
                t ? (jQuery("body").removeClass("slidebar"), jQuery(".sidebar").removeClass("sidebar-fixed"), o && (jQuery("#header").addClass("navbar-fixed-top"), jQuery("#main-content").addClass("margin-top-100")), t = !1, $.cookie("mini_sidebar", "0")) : (jQuery("body").addClass("slidebar"), jQuery(".sidebar").addClass("sidebar-fixed"), o && (jQuery("#header").removeClass("navbar-fixed-top"), jQuery("#main-content").removeClass("margin-top-100")), t = !0, $.cookie("mini_sidebar", "1"), p());
            else {
                var e = document.getElementById("sidebar-collapse").querySelector('[class*="fa-"]'), n = e.getAttribute("data-icon1"), r = e.getAttribute("data-icon2");
                t ? (jQuery(".navbar-brand").removeClass("mini-menu"), jQuery("#sidebar").removeClass("mini-menu"), jQuery("#main-content").removeClass("margin-left-50"), jQuery(".sidebar-collapse i").removeClass(r), jQuery(".sidebar-collapse i").addClass(n), jQuery(".search").attr("placeholder", "Search"), t = !1, $.cookie("mini_sidebar", "0")) : (jQuery(".navbar-brand").addClass("mini-menu"), jQuery("#sidebar").addClass("mini-menu"), jQuery("#main-content").addClass("margin-left-50"), jQuery(".sidebar-collapse i").removeClass(n), jQuery(".sidebar-collapse i").addClass(r), jQuery(".search").attr("placeholder", ""), t = !0, $.cookie("mini_sidebar", "1")), $("#main-content").on("resize", function (e) {
                    e.stopPropagation()
                })
            }
        })
    }
	
	, p = function () {
        var e = $(".sidebar");
        1 === e.parent(".slimScrollDiv").size() && (e.slimScroll({destroy: !0}), e.removeAttr("style"), $("#sidebar").removeAttr("style")), e.slimScroll({size: "7px", color: "#a1b2bd", opacity: .3, height: "100%", allowPageScroll: !1, disableFadeOut: !1})
    }
	
	, f = function () {
        var e = $(".sidebar-menu");
        if (1 === e.parent(".slimScrollDiv").size() && (e.slimScroll({destroy: !0}), e.removeAttr("style"), $("#sidebar").removeAttr("style")), 0 === $(".sidebar-fixed").size())
            return c(), void 0;
        var t = s();
        if (t.width >= 992) {
            var a = $(window).height() - $("#header").height() + 1;
            e.slimScroll({size: "7px", color: "#a1b2bd", opacity: .3, height: a, allowPageScroll: !1, disableFadeOut: !1}), c()
        }
    };
	
    jQuery(window).resize(function () {
        setTimeout(function () {
            l(), c(), u(), f(), pt(), r()
        }, 50)
    });
	
    var g = function () {
        $("#reportrange").daterangepicker({startDate: moment().subtract("days", 29), endDate: moment(), minDate: "01/01/2012", maxDate: "12/31/2014", dateLimit: {days: 60}, showDropdowns: !0, showWeekNumbers: !0, timePicker: !1, timePickerIncrement: 1, timePicker12Hour: !0, ranges: {Yesterday: [moment().subtract("days", 1), moment().subtract("days", 1)], "Last 30 Days": [moment().subtract("days", 29), moment()], "This Month": [moment().startOf("month"), moment().endOf("month")]}, opens: "left", buttonClasses: ["btn btn-default"], applyClass: "btn-small btn-primary", cancelClass: "btn-small", format: "MM/DD/YYYY", separator: " to ", locale: {applyLabel: "Submit", fromLabel: "From", toLabel: "To", customRangeLabel: "Custom Range", daysOfWeek: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"], monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], firstDay: 1}}, function (e, t) {
            console.log("Callback has been called!"), $("#reportrange span").html(e.format("MMMM D, YYYY") + " - " + t.format("MMMM D, YYYY"))
        }), $("#reportrange span").html("Custom")
    }
	
	, b = function () {
        v(), $(".team-status-toggle").click(function (a) {
            a.preventDefault(), e(this), $(this).parent().toggleClass("open");
            var i = t(this);
            $(i).slideToggle(200, function () {
                $(this).toggleClass("open")
            })
        }), $("body").click(function (t) {
            var a = t.target.className.split(" ");
            -1 == $.inArray("team-status", a) && -1 == $.inArray("team-status-toggle", a) && -1 == $(t.target).parents().index($(".team-status")) && 0 == $(t.target).parents(".team-status-toggle").length && e()
        }), $(".team-status #teamslider").each(function () {
            $(this).slimScrollHorizontal({width: "100%", alwaysVisible: !0, color: "#fff", opacity: "0.5", size: "5px"})
        });
        var e = function (e) {
            $(".team-status").each(function () {
                var a = $(this);
                if (a.is(":visible")) {
                    var i = t(e);
                    i != "#" + a.attr("id") && $(this).slideUp(200, function () {
                        $(this).toggleClass("open"), $(".team-status-toggle").each(function () {
                            var e = t(this);
                            e == "#" + a.attr("id") && $(this).parent().removeClass("open")
                        })
                    })
                }
            })
        }, t = function (e) {
            var t = $(e).data("teamStatus");
            return"undefined" == typeof t && (t = "#team-status"), t
        }
    }
	
	, v = function () {
        $(".team-status").each(function () {
            var e = $(this);
            e.css("position", "absolute").css("margin-top", "-1000px").show();
            var t = 0;
            $("ul li", this).each(function () {
                t += $(this).outerWidth(!0) + 15
            }), e.css("position", "relative").css("margin-top", "0").hide(), $("ul", this).width(t)
        })
    }
	
	, y = function () {
        $(".tip").tooltip(), $(".tip-bottom").tooltip({placement: "bottom"}), $(".tip-left").tooltip({placement: "left"}), $(".tip-right").tooltip({placement: "right"}), $(".tip-focus").tooltip({trigger: "focus"})
    }
	
	, x = function () {
        jQuery(".box .tools .collapse, .box .tools .expand").click(function () {
            var e = jQuery(this).parents(".box").children(".box-body");
            if (jQuery(this).hasClass("collapse")) {
                jQuery(this).removeClass("collapse").addClass("expand");
                var t = jQuery(this).children(".fa-chevron-up");
                t.removeClass("fa-chevron-up").addClass("fa-chevron-down"), e.slideUp(200)
            } else {
                jQuery(this).removeClass("expand").addClass("collapse");
                var t = jQuery(this).children(".fa-chevron-down");
                t.removeClass("fa-chevron-down").addClass("fa-chevron-up"), e.slideDown(200)
            }
        }), jQuery(".box .tools a.remove").click(function () {
            var e = jQuery(this).parents(".box");
            e.next().hasClass("box") || e.prev().hasClass("box") ? jQuery(this).parents(".box").remove() : jQuery(this).parents(".box").parent().remove()
        }), jQuery(".box .tools a.reload").click(function () {
            var e = jQuery(this).parents(".box");
            App.blockUI(e), window.setTimeout(function () {
                App.unblockUI(e)
            }, 1e3)
        })
    }
	
	, w = function () {
        jQuery().slimScroll && $(".scroller").each(function () {
            $(this).slimScroll({size: "7px", color: "#a1b2bd", height: $(this).attr("data-height"), alwaysVisible: "1" == $(this).attr("data-always-visible") ? !0 : !1, railVisible: "1" == $(this).attr("data-rail-visible") ? !0 : !1, railOpacity: .1, disableFadeOut: !0})
        })
    }
	
	, C = function () {
        $(".basic-alert").click(function () {
            bootbox.alert("Hello World")
        }), $(".confirm-dialog").click(function () {
            bootbox.confirm("Are you sure?", function () {
            })
        }), $(".multiple-buttons").click(function () {
            bootbox.dialog({message: "I am a custom dialog", title: "Custom title", buttons: {success: {label: "Success!", className: "btn-success", callback: function () {
                            Example.show("great success")
                        }}, danger: {label: "Danger!", className: "btn-danger", callback: function () {
                            Example.show("uh oh, look out!")
                        }}, main: {label: "Click ME!", className: "btn-primary", callback: function () {
                            Example.show("Primary button")
                        }}}})
        }), $(".multiple-dialogs").click(function () {
            bootbox.alert("In 1 second a new modal will open"), setTimeout(function () {
                bootbox.dialog({message: "Will you purchase this awesome theme", title: "Pop quiz", buttons: {success: {label: "Yes!", className: "btn-success", callback: function () {
                                bootbox.alert("Congratulations! You made the right decision.", function () {
                                    $(".bootbox").modal("hide")
                                })
                            }}, danger: {label: "No!", className: "btn-danger", callback: function () {
                                bootbox.alert("Oops, we're sorry to hear that!", function () {
                                    $(".bootbox").modal("hide")
                                })
                            }}, main: {label: "Click ME!", className: "btn-primary", callback: function () {
                                bootbox.alert("Hello World", function () {
                                    $(".bootbox").modal("hide")
                                })
                            }}}})
            }, 1e3)
        }), $(".programmatic-close").click(function () {
            bootbox.alert("In 3 second this modal will close.."), setTimeout(function () {
                $(".bootbox").modal("hide")
            }, 3e3)
        })
    }
	
	, k = function () {
        $(".pop").popover(), $(".pop-bottom").popover({placement: "bottom"}), $(".pop-left").popover({placement: "left"}), $(".pop-top").popover({placement: "top"}), $(".pop-hover").popover({trigger: "hover"})
    }
	
	, T = function () {
        $("#normal").click(function () {
            var e = $("input[name=theme]:checked").val(), t = $("input[name=position]:checked").val();
            Messenger.options = {extraClasses: "messenger-fixed " + t, theme: e}, Messenger().post({message: "This is a normal notification!", showCloseButton: !0})
        }), $("#interactive").click(function () {
            var e = $("input[name=theme]:checked").val(), t = $("input[name=position]:checked").val();
            Messenger.options = {extraClasses: "messenger-fixed " + t, theme: e};
            var a;
            a = Messenger().post({message: "Launching thermonuclear war...", type: "info", actions: {cancel: {label: "cancel launch", action: function () {
                            return a.update({message: "Thermonuclear war averted", type: "success", showCloseButton: !0, actions: !1})
                        }}}})
        }), $("#timer").click(function () {
            var e = $("input[name=theme]:checked").val(), t = $("input[name=position]:checked").val();
            Messenger.options = {extraClasses: "messenger-fixed " + t, theme: e};
            var a;
            a = 0, Messenger().run({errorMessage: "Error destroying alien planet", successMessage: "Alien planet destroyed!", showCloseButton: !0, action: function (e) {
                    return++a < 3 ? e.error({status: 500, readyState: 0, responseText: 0}) : e.success()
                }})
        }), $("#prompts").click(function () {
            var e = $("input[name=theme]:checked").val(), t = $("input[name=position]:checked").val();
            Messenger.options = {extraClasses: "messenger-fixed " + t, theme: e}, Messenger().run({successMessage: "Data saved.", errorMessage: "Error saving data", progressMessage: "Saving data", showCloseButton: !0}, {url: "http://www.example.com/data"})
        })
    }
	
	, j = function () {
        $(".alert").alert()
    }
	
	, S = function () {
        for (var e = [], t = "New York,Los Angeles,Chicago,Houston,Paris,Marseille,Toulouse,Lyon,Bordeaux,Philadelphia,Phoenix,San Antonio,San Diego,Dallas,San Jose,Jacksonville".split(","), a = 0; a < t.length; a++)
            e.push({id: a, name: t[a], status: a % 2 ? "Already Visited" : "Planned for visit", coolness: Math.floor(10 * Math.random()) + 1});
        $("#ms1").magicSuggest({data: e, sortOrder: "name", value: [0], selectionPosition: "right", groupBy: "status", maxDropHeight: 200}), $("#ms2").magicSuggest({width: "80%", data: e}), $("#ms3").magicSuggest({selectionPosition: "bottom", renderer: function (e) {
                return'<div><div style="font-family: Arial; font-weight: bold">' + e.name + "</div><div><b>Cooooolness</b>: " + e.coolness + "</div></div>"
            }, minChars: 1, selectionStacked: !0, data: e}), $("#ms4").magicSuggest({data: [{id: 1, label: "one"}, {id: 2, label: "two"}, {id: 3, label: "three"}], displayField: "label", value: [1, 3]}), $("#ms5").magicSuggest({width: "80%", data: "marilyn@monroe.com,mother@teresa.com,john@kennedy.com,martin@luther.com,nelson@mandela.com,winston@churchill.com,bill@gates.com,muhammad@ali.com,mahatma@gandhi.com,margaret@thatcher.com,charles@gaulle.com,christopher@colombus.com,george@orwell.com,charles@darwin.com,elvis@presley.com,albert@einstein.com,paul@mccartney.com,queen@elizabeth.com,queen@victoria.com,john@keynes.com,mikhail@gorbachev.com,jawaharlal@nehru.com,leonardo@vinci.com,louis@pasteur.com,leo@tolstoy.com,pablo@picasso.com,vincent@gogh.com,franklin@roosevelt.com,john@paul.com,neil@armstrong.com,thomas@edison.com,rosa@parks.com,aung@kyi.com,lyndon@johnson.com,ludwig@beethoven.com,oprah@winfrey.com,indira@gandhi.com,eva@peron.com,benazir@bhutto.com,desmond@tutu.com,dalai@lama.com,walt@disney.com,peter@sellers.com,barack@obama.com,malcolm@x.com,richard@branson.com,jesse@owens.com,ernest@hemingway.com,john@lennon.com,henry@ford.com,haile@selassie.com,joseph@stalin.com,lord@baden.com,michael@jordon.com,george@bush.com,osama@laden.com,fidel@castro.com,oscar@wilde.com,coco@chanel.com,amelia@earhart.com,adolf@hitler.com,mary@magdalene.com,alfred@hitchcock.com,michael@jackson.com,mata@hari.com,emmeline@pankhurst.com,ronald@reagan.com,lionel@messi.com,babe@ruth.com,bob@geldof.com,leon@trotsky.com,roger@federer.com,sigmund@freud.com,woodrow@wilson.com,mao@zedong.com,katherine@hepburn.com,audrey@hepburn.com,david@beckham.com,tiger@woods.com,usain@bolt.com,bill@cosby.com,carl@lewis.com,prince@charles.com,jacqueline@onassis.com,billie@holiday.com,virginia@woolf.com,billie@king.com,kylie@minogue.com,anne@frank.com,emile@zatopek.com,lech@walesa.com,christiano@ronaldo.com,yoko@ono.com,julie@andrews.com,florence@nightingale.com,marie@curie.com,stephen@hawking.com,tim@lee.com,lady@gaga.com,lance@armstrong.com,jon@stewart.com,scarlett@johansson.com,larry@page.com,sergey@brin.com,roman@abramovich.com,rupert@murdoch.com,al@gore.com,sacha@baron.com,george@clooney.com,paul@krugman.com,jimmy@wales.com"}), $("#ms6").magicSuggest({}), $("#ms7").magicSuggest({data: e, resultAsString: !0, maxSelection: 1, maxSelectionRenderer: function () {
            }})
    }
	
	, A = function () {
        jQuery(document).ready(function () {
            var e = moment().format("YYYY-MM-DD HH:mm"), t = moment().subtract("days", 1).format("MMM D, YYYY");
            $("#curr-time").html(e), $("#curr-time").attr("title", e), $("#curr-time").attr("data-original-title", e), $("#yesterday").html(t), $("#yesterday").attr("title", t), $("#yesterday").attr("data-original-title", t), jQuery("abbr.timeago").timeago()
        })
    }
	
	, _ = function () {
        jQuery("abbr.timeago").timeago()
    }
	
	, D = function () {
        $(".datepicker").datepicker(), $(".inlinepicker").datepicker({inline: !0, showOtherMonths: !0}), $(".datepicker-fullscreen").pickadate(), $(".timepicker-fullscreen").pickatime(), $(".colorpicker").colorpicker();
        var e = $("#color-pickers")[0].style;
        $("#colorpicker-event").colorpicker().on("changeColor", function (t) {
            e.backgroundColor = t.color.toHex()
        })
    }
	
	, M = function () {
        $.fn.raty.defaults.path = "js/jquery-raty/img", $("#score-demo").raty({score: 3}), $("#number-demo").raty({number: 10}), $("#readOnly-demo").raty({readOnly: !0, score: 2}), $("#halfShow-true-demo").raty({score: 3.26}), $("#starHalf-demo").raty({path: "js/jquery-raty/img", half: !0, starOff: "cookie-off.png", starOn: "cookie-on.png", starHalf: "cookie-half.png"}), $("#star-off-and-star-on-demo").raty({path: "js/jquery-raty/img", starOff: "off.png", starOn: "on.png"}), $("#cancel-off-and-cancel-on-demo").raty({path: "js/jquery-raty/img", cancel: !0, cancelOff: "cancel-custom-off.png", cancelOn: "cancel-custom-on.png", starOn: "star-on.png", starOff: "star-off.png"}), $("#size-demo").raty({path: "js/jquery-raty/img", cancel: !0, cancelOff: "cancel-off-big.png", cancelOn: "cancel-on-big.png", half: !0, size: 24, starHalf: "star-half-big.png", starOff: "star-off-big.png", starOn: "star-on-big.png"}), $("#target-div-demo").raty({cancel: !0, target: "#target-div-hint"})
    }
	
	, Q = function () {
        $(document).ready(function () {
            $("#btn-load").on("click", function () {
                var e = $(this);
                e.button("loading"), setTimeout(function () {
                    e.button("reset")
                }, 1500)
            }), $("#btn-load-complete").on("click", function () {
                var e = $(this);
                e.button("loading"), setTimeout(function () {
                    e.button("complete")
                }, 1500)
            })
        })
    }
	
	, P = function () {
        $(".radio1").on("switch-change", function () {
            $(".radio1").bootstrapSwitch("toggleRadioState")
        }), $(".radio1").on("switch-change", function () {
            $(".radio1").bootstrapSwitch("toggleRadioStateAllowUncheck")
        }), $(".radio1").on("switch-change", function () {
            $(".radio1").bootstrapSwitch("toggleRadioStateAllowUncheck", !1)
        })
    }
	
	, z = function () {
        function e(e, t) {
            var a = $(t.handle).data("bs.tooltip").$tip[0], i = $.extend({}, $(t.handle).offset(), {width: $(t.handle).get(0).offsetWidth, height: $(t.handle).get(0).offsetHeight}), o = a.offsetWidth;
            tp = {left: i.left + i.width / 2 - o / 2}, $(a).offset(tp), $(a).find(".tooltip-inner").text(t.value)
        }
        $("#slider").slider({value: 15, slide: e, stop: e}), $("#slider .ui-slider-handle:first").tooltip({title: $("#slider").slider("value"), trigger: "manual"}).tooltip("show"), $("#slider-default").slider(), $("#slider-range").slider({range: !0, min: 0, max: 500, values: [75, 300]}), $("#slider-range-min").slider({range: "min", value: 37, min: 1, max: 700, slide: function (e, t) {
                $("#slider-range-min-amount").text("$" + t.value)
            }}), $("#slider-range-max").slider({range: "max", min: 1, max: 700, value: 300, slide: function (e, t) {
                $("#slider-range-max-amount").text("$" + t.value)
            }}), $("#slider-vertical-multiple > span").each(function () {
            var e = parseInt($(this).text(), 10);
            $(this).empty().slider({value: e, range: "min", animate: !0, orientation: "vertical"})
        }), $("#slider-vertical-range-min").slider({range: "min", value: 400, min: 1, max: 600, orientation: "vertical"}), $("#slider-horizontal-range-min").slider({range: "min", value: 600, min: 1, max: 1e3})
    }
	
	, I = function () {
        $(document).ready(function () {
            jQuery.fn.anim_progressbar = function (e) {
                var t = 1e3, a = 60 * t, i = 3600 * t, o = 86400 * t, n = {start: new Date, finish: (new Date).setTime((new Date).getTime() + 60 * t), interval: 100}, r = jQuery.extend(n, e), s = this;
                return this.each(function () {
                    var e = r.finish - r.start;
                    $(s).children(".pbar").progressbar();
                    var n = setInterval(function () {
                        var l = r.finish - new Date, c = new Date - r.start, d = parseInt(l / o), m = parseInt((l - d * o) / i), u = parseInt((l - d * o - m * i) / a), h = parseInt((l - d * o - u * a - m * i) / t), p = c > 0 ? c / e * 100 : 0;
                        $(s).children(".percent").html("<b>" + p.toFixed(1) + "%</b>"), $(s).children(".elapsed").html(d + " day " + m + " hr : " + u + " min : " + h + " sec remaining</b>"), $(s).children(".pbar").children(".ui-progressbar-value").css("width", p + "%"), p >= 100 && (clearInterval(n), $(s).children(".percent").html("<b>100%</b>"), $(s).children(".elapsed").html("Completed"))
                    }, r.interval)
                })
            }, $("#progress1").anim_progressbar();
            var e = (new Date).setTime((new Date).getTime() + 5e3), t = (new Date).setTime((new Date).getTime() + 15e3);
            $("#progress2").anim_progressbar({start: e, finish: t, interval: 100}), $("#progress3").anim_progressbar({interval: 1e3})
        })
    }
	
	, F = function () {
        $(".knob").knob({change: function () {
            }, release: function (e) {
                console.log("release : " + e)
            }, cancel: function () {
                console.log("cancel : ", this)
            }, draw: function () {
                if ("tron" == this.$.data("skin")) {
                    var e, t = this.angle(this.cv), a = this.startAngle, i = this.startAngle, o = i + t, n = 1;
                    return this.g.lineWidth = this.lineWidth, this.o.cursor && (i = o - .3) && (o += .3), this.o.displayPrevious && (e = this.startAngle + this.angle(this.v), this.o.cursor && (a = e - .3) && (e += .3), this.g.beginPath(), this.g.strokeStyle = this.pColor, this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, a, e, !1), this.g.stroke()), this.g.beginPath(), this.g.strokeStyle = n ? this.o.fgColor : this.fgColor, this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, i, o, !1), this.g.stroke(), this.g.lineWidth = 2, this.g.beginPath(), this.g.strokeStyle = this.o.fgColor, this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + 2 * this.lineWidth / 3, 0, 2 * Math.PI, !1), this.g.stroke(), !1
                }
            }})
    }
	
	, W = function () {
        var e = function (e) {
            $(e).each(function () {
                var e = $($($(this).attr("href"))), t = $(this).parent().parent();
                t.height() > e.height() && e.css("min-height", t.height())
            })
        };
        if ($("body").on("click", '.nav.nav-tabs.tabs-left a[data-toggle="tab"], .nav.nav-tabs.tabs-right a[data-toggle="tab"]', function () {
            e($(this))
        }), e('.nav.nav-tabs.tabs-left > li.active > a[data-toggle="tab"], .nav.nav-tabs.tabs-right > li.active > a[data-toggle="tab"]'), location.hash) {
            var t = location.hash.substr(1);
            $('a[href="#' + t + '"]').click()
        }
    }
	
	, O = function () {
        $("#tree1").admin_tree({dataSource: treeDataSource, multiSelect: !0, loadingHTML: '<div class="tree-loading"><i class="fa fa-spinner fa-2x fa-spin"></i></div>', "open-icon": "fa-minus", "close-icon": "fa-plus", selectable: !0, "selected-icon": "fa-check", "unselected-icon": "fa-times"}), $("#tree3").admin_tree({dataSource: treeDataSource3, multiSelect: !0, loadingHTML: '<div class="tree-loading"><i class="fa fa-spinner fa-2x fa-spin"></i></div>', "open-icon": "fa-minus-square", "close-icon": "fa-plus-square", selectable: !0, "selected-icon": "fa-check", "unselected-icon": "fa-times"}), $("#tree2").admin_tree({dataSource: treeDataSource2, loadingHTML: '<div class="tree-loading"><i class="fa fa-spinner fa-2x fa-spin"></i></div>', "open-icon": "fa-folder-open", "close-icon": "fa-folder", selectable: !1, "selected-icon": null, "unselected-icon": null}), $(".tree").find('[class*="fa-"]').addClass("fa")
    }
	
	, N = function () {
        var e = function (e) {
            var t = e.length ? e : $(e.target), a = t.data("output");
            window.JSON ? a.val(window.JSON.stringify(t.nestable("serialize"))) : a.val("JSON browser support required for this demo.")
        };
        $("#nestable").nestable({group: 1}).on("change", e), $("#nestable2").nestable({group: 1}).on("change", e), e($("#nestable").data("output", $("#nestable-output"))), e($("#nestable2").data("output", $("#nestable2-output"))), $("#nestable-menu").on("click", function (e) {
            var t = $(e.target), a = t.data("action");
            "expand-all" === a && $(".dd").nestable("expandAll"), "collapse-all" === a && $(".dd").nestable("collapseAll")
        }), $("#nestable3").nestable()
    }
	
	, E = function () {
        $("#example-dark").tablecloth({theme: "dark"}), $("#example-paper").tablecloth({theme: "paper", striped: !0}), $("#example-stats").tablecloth({theme: "stats", sortable: !0, condensed: !0, striped: !0, clean: !0})
    }
	
	, B = function () {
        $("#datatable1").dataTable({sPaginationType: "bs_full"}), $("#datatable2").dataTable({sPaginationType: "bs_full", sDom: "<'row'<'dataTables_header clearfix'<'col-md-4'l><'col-md-8'Tf>r>>t<'row'<'dataTables_footer clearfix'<'col-md-6'i><'col-md-6'p>>>", oTableTools: {aButtons: ["copy", "print", "csv", "xls", "pdf"], sSwfPath: "js/datatables/extras/TableTools/media/swf/copy_csv_xls_pdf.swf"}}), $(".datatable").each(function () {
            var e = $(this), t = e.closest(".dataTables_wrapper").find("div[id$=_filter] input");
            t.attr("placeholder", "Search"), t.addClass("form-control input-sm");
            var a = e.closest(".dataTables_wrapper").find("div[id$=_length] select");
            a.addClass("form-control input-sm")
        })
    }
	
	, Y = function () {
        var e = [{id: "1", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "2", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "3", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "4", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "5", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "6", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "7", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "8", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "9", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "10", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "11", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "12", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "13", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "14", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "15", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "16", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "17", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "18", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "19", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "20", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "21", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "22", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "23", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "24", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}, {id: "25", invdate: "2007-12-03", name: "Client1", amount: "1000.00", tax: "140.00", total: "1000.00", note: "This is a note"}];
        jQuery("#rowed3").jqGrid({data: e, datatype: "local", height: 250, colNames: ["Inv No", "Date", "Client", "Amount", "Tax", "Total", "Notes"], colModel: [{name: "id", index: "id", width: 55}, {name: "invdate", index: "invdate", width: 90, editable: !0}, {name: "name", index: "name", width: 100, editable: !0}, {name: "amount", index: "amount", width: 80, align: "right", editable: !0}, {name: "tax", index: "tax", width: 80, align: "right", editable: !0}, {name: "total", index: "total", width: 80, align: "right", editable: !0}, {name: "note", index: "note", width: 150, sortable: !1, editable: !0}], rowNum: 10, rowList: [10, 20, 30], pager: "#prowed3", sortname: "id", viewrecords: !0, sortorder: "asc", editurl: "server.html", caption: "Inline navigator", autowidth: !0}), jQuery("#rowed3").jqGrid("navGrid", "#prowed3", {edit: !1, add: !1, del: !1}), jQuery("#rowed3").jqGrid("inlineNav", "#prowed3"), $(".navtable .ui-pg-button").tooltip({container: "body"})
    }
	
	, R = function () {
        $("#autocomplete-example").typeahead({name: "countries", local: ["red", "blue", "green", "yellow", "brown", "black"]})
    }
	
	, L = function () {
        $("textarea.autosize").autosize(), $("textarea.autosize").addClass("textarea-transition")
    }
	
	, U = function () {
        $(".countable").simplyCountable()
    }
	
	, H = function () {
        function e(e) {
            var t = "<table class='movie-result'><tr>";
            return void 0 !== e.posters && void 0 !== e.posters.thumbnail && (t += "<td class='movie-image'><img src='" + e.posters.thumbnail + "'/></td>"), t += "<td class='movie-info'><div class='movie-title'>" + e.title + "</div>", void 0 !== e.critics_consensus ? t += "<div class='movie-synopsis'>" + e.critics_consensus + "</div>" : void 0 !== e.synopsis && (t += "<div class='movie-synopsis'>" + e.synopsis + "</div>"), t += "</td></tr></table>"
        }
        function t(e) {
            return e.title
        }
        $("#e1").select2(), $("#e2").select2(), $("#e3").select2({placeholder: "Select a State", allowClear: !0}), $("#e4").select2({placeholder: "Select a State"}), $("#e5").select2({placeholder: "Select 2 characters", minimumInputLength: 2}), $("#e6").select2({placeholder: "Select a maximum of 3 states", maximumSelectionSize: 3}), $("#e7").select2({placeholder: "Search for a movie", minimumInputLength: 1, ajax: {url: "http://api.rottentomatoes.com/api/public/v1.0/movies.json", dataType: "jsonp", data: function (e) {
                    return{q: e, page_limit: 10, apikey: "uekzdmffsrmqzwdtcgmc5yu9"}
                }, results: function (e) {
                    return{results: e.movies}
                }}, initSelection: function (e, t) {
                var a = $(e).val();
                "" !== a && $.ajax("http://api.rottentomatoes.com/api/public/v1.0/movies/" + a + ".json", {data: {apikey: "uekzdmffsrmqzwdtcgmc5yu9"}, dataType: "jsonp"}).done(function (e) {
                    t(e)
                })
            }, formatResult: e, formatSelection: t, dropdownCssClass: "bigdrop", escapeMarkup: function (e) {
                return e
            }}), $("#e8").select2({tags: ["red", "green", "blue"]})
    }
	
	, q = function () {
        $(".uniform").uniform()
    }
	
	, J = function () {
        $("select, input[type='checkbox']").uniform()
    }
	
	, G = function () {
        function e() {
            var e = ["Serif", "Sans", "Arial", "Arial Black", "Courier", "Courier New", "Comic Sans MS", "Helvetica", "Impact", "Lucida Grande", "Lucida Sans", "Tahoma", "Times", "Times New Roman", "Verdana"], t = $("[title=Font]").siblings(".dropdown-menu");
            if ($.each(e, function (e, a) {
                t.append($('<li><a data-edit="fontName ' + a + '" style="font-family:\'' + a + "'\">" + a + "</a></li>"))
            }), $("a[title]").tooltip({container: "body"}), $(".dropdown-menu input").click(function () {
                return!1
            }).change(function () {
                $(this).parent(".dropdown-menu").siblings(".dropdown-toggle").dropdown("toggle")
            }).keydown("esc", function () {
                this.value = "", $(this).change()
            }), $("[data-role=magic-overlay]").each(function () {
                var e = $(this), t = $(e.data("target"));
                e.css("opacity", 0).css("position", "absolute").offset(t.offset()).width(t.outerWidth()).height(t.outerHeight())
            }), "onwebkitspeechchange"in document.createElement("input")) {
                var a = $("#editor").offset();
                $("#voiceBtn").css("position", "absolute").offset({top: a.top, left: a.left + $("#editor").innerWidth() - 35})
            } else
                $("#voiceBtn").hide()
        }
        function t(e, t) {
            var a = "";
            "unsupported-file-type" === e ? a = "Unsupported format " + t : console.log("error uploading file", e, t), $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button><strong>File upload error</strong> ' + a + " </div>").prependTo("#alerts")
        }
        e(), $("#editor").wysiwyg({fileUploadError: t}), CKEDITOR.disableAutoInline = !0
    }
	
	, V = function () {
        try {
            $(".dropzone").dropzone({paramName: "file", maxFilesize: .5, addRemoveLinks: !0, dictResponseError: "Error while uploading file!", previewTemplate: '<div class="dz-preview dz-file-preview">\n  <div class="dz-details">\n    <div class="dz-filename"><span data-dz-name></span></div>\n    <div class="dz-size" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class="progress progress-sm progress-striped active"><div class="progress-bar progress-bar-success" data-dz-uploadprogress></div></div>\n  <div class="dz-success-mark"><span></span></div>\n  <div class="dz-error-mark"><span></span></div>\n  <div class="dz-error-message"><span data-dz-errormessage></span></div>\n</div>'})
        } catch (e) {
            alert("Dropzone.js does not support older browsers!")
        }
    }
	
	, X = function () {
        function e() {
            function e(e) {
                var t = i[e];
                return s.setData(t), l.classed("toggled", function () {
                    return d3.select(this).attr("data-type") === t.type
                }), t
            }
            function t() {
                n += 1, n = n >= o.length ? 0 : n;
                e(o[n]);
                a = setTimeout(t, c)
            }
            var a, i = [{xScale: "ordinal", comp: [], main: [{className: ".main.l1", data: [{y: 15, x: "2012-11-19T00:00:00"}, {y: 11, x: "2012-11-20T00:00:00"}, {y: 8, x: "2012-11-21T00:00:00"}, {y: 10, x: "2012-11-22T00:00:00"}, {y: 1, x: "2012-11-23T00:00:00"}, {y: 6, x: "2012-11-24T00:00:00"}, {y: 8, x: "2012-11-25T00:00:00"}]}, {className: ".main.l2", data: [{y: 29, x: "2012-11-19T00:00:00"}, {y: 33, x: "2012-11-20T00:00:00"}, {y: 13, x: "2012-11-21T00:00:00"}, {y: 16, x: "2012-11-22T00:00:00"}, {y: 7, x: "2012-11-23T00:00:00"}, {y: 18, x: "2012-11-24T00:00:00"}, {y: 8, x: "2012-11-25T00:00:00"}]}], type: "line-dotted", yScale: "linear"}, {xScale: "ordinal", comp: [], main: [{className: ".main.l1", data: [{y: 12, x: "2012-11-19T00:00:00"}, {y: 18, x: "2012-11-20T00:00:00"}, {y: 8, x: "2012-11-21T00:00:00"}, {y: 7, x: "2012-11-22T00:00:00"}, {y: 6, x: "2012-11-23T00:00:00"}, {y: 12, x: "2012-11-24T00:00:00"}, {y: 8, x: "2012-11-25T00:00:00"}]}, {className: ".main.l2", data: [{y: 29, x: "2012-11-19T00:00:00"}, {y: 33, x: "2012-11-20T00:00:00"}, {y: 13, x: "2012-11-21T00:00:00"}, {y: 16, x: "2012-11-22T00:00:00"}, {y: 7, x: "2012-11-23T00:00:00"}, {y: 18, x: "2012-11-24T00:00:00"}, {y: 8, x: "2012-11-25T00:00:00"}]}], type: "cumulative", yScale: "linear"}, {xScale: "ordinal", comp: [], main: [{className: ".main.l1", data: [{y: 12, x: "2012-11-19T00:00:00"}, {y: 18, x: "2012-11-20T00:00:00"}, {y: 8, x: "2012-11-21T00:00:00"}, {y: 7, x: "2012-11-22T00:00:00"}, {y: 6, x: "2012-11-23T00:00:00"}, {y: 12, x: "2012-11-24T00:00:00"}, {y: 8, x: "2012-11-25T00:00:00"}]}, {className: ".main.l2", data: [{y: 29, x: "2012-11-19T00:00:00"}, {y: 33, x: "2012-11-20T00:00:00"}, {y: 13, x: "2012-11-21T00:00:00"}, {y: 16, x: "2012-11-22T00:00:00"}, {y: 7, x: "2012-11-23T00:00:00"}, {y: 18, x: "2012-11-24T00:00:00"}, {y: 8, x: "2012-11-25T00:00:00"}]}], type: "bar", yScale: "linear"}], o = [0, 1, 0, 2], n = 0, r = d3.time.format("%A"), s = new xChart("line-dotted", i[o[n]], "#chart1", {axisPaddingTop: 5, dataFormatX: function (e) {
                    return new Date(e)
                }, tickFormatX: function (e) {
                    return r(e)
                }, timing: 1250}), l = d3.selectAll(".multi button"), c = 3500;
            l.on("click", function (t, i) {
                clearTimeout(a), e(i)
            }), a = setTimeout(t, c)
        }
        function t() {
            {
                var e = {xScale: "time", yScale: "linear", type: "line", main: [{className: ".pizza", data: [{x: "2012-11-05", y: 1}, {x: "2012-11-06", y: 6}, {x: "2012-11-07", y: 13}, {x: "2012-11-08", y: -3}, {x: "2012-11-09", y: -4}, {x: "2012-11-10", y: 9}, {x: "2012-11-11", y: 6}]}]}, t = {dataFormatX: function (e) {
                        return d3.time.format("%Y-%m-%d").parse(e)
                    }, tickFormatX: function (e) {
                        return d3.time.format("%A")(e)
                    }};
                new xChart("line", e, "#chart2", t)
            }
        }
        function a() {
            var e = document.createElement("div"), t = -(~~$("html").css("padding-left").replace("px", "") + ~~$("body").css("margin-left").replace("px", "")), a = -32;
            e.className = "ex-tooltip", document.body.appendChild(e);
            {
                var i = {xScale: "time", yScale: "linear", main: [{className: ".pizza", data: [{x: "2012-11-05", y: 6}, {x: "2012-11-06", y: 6}, {x: "2012-11-07", y: 8}, {x: "2012-11-08", y: 3}, {x: "2012-11-09", y: 4}, {x: "2012-11-10", y: 9}, {x: "2012-11-11", y: 6}]}]}, o = {dataFormatX: function (e) {
                        return d3.time.format("%Y-%m-%d").parse(e)
                    }, tickFormatX: function (e) {
                        return d3.time.format("%A")(e)
                    }, mouseover: function (i) {
                        var o = $(this).offset();
                        $(e).text(d3.time.format("%A")(i.x) + ": " + i.y).css({top: a + o.top, left: o.left + t}).show()
                    }, mouseout: function () {
                        $(e).hide()
                    }};
                new xChart("line-dotted", i, "#chart3", o)
            }
        }
        function i() {
            {
                var e = {xScale: "ordinal", yScale: "linear", main: [{className: ".pizza", data: [{x: "Pepperoni", y: 4}, {x: "Cheese", y: 8}]}]};
                new xChart("bar", e, "#chart4")
            }
        }
        function o() {
            {
                var e = {xScale: "ordinal", yScale: "linear", main: [{className: ".pizza", data: [{x: "Pepperoni", y: 4}, {x: "Cheese", y: 8}]}, {className: ".pizza", data: [{x: "Pepperoni", y: 6}, {x: "Cheese", y: 5}]}]};
                new xChart("bar", e, "#chart5")
            }
        }
        function n() {
            function e() {
                setTimeout(function () {
                    e(), o += 1, i.setData(a[o % 2])
                }, 3e3)
            }
            var t = {enter: function (e, t, a, i) {
                    var o, n, r = xChart.visutils.getInsertionPoint(9), s = i.map(function (e) {
                        return e.data = e.data.map(function (e) {
                            return[{x: e.x, y: e.y - e.e}, {x: e.x, y: e.y}, {x: e.x, y: e.y + e.e}]
                        }), e
                    });
                    o = e._g.selectAll(".errorLine" + a).data(s, function (e) {
                        return e.className
                    }), o.enter().insert("g", r).attr("class", function (e, t) {
                        return"errorLine" + a.replace(/\./g, " ") + " color" + t
                    }), n = o.selectAll("path").data(function (e) {
                        return e.data
                    }, function (e) {
                        return e[0].x
                    }), n.enter().insert("path").style("opacity", 0).attr("d", d3.svg.line().x(function (t) {
                        return e.xScale(t.x) + e.xScale.rangeBand() / 2
                    }).y(function (t) {
                        return e.yScale(t.y)
                    })), t.containers = o, t.paths = n
                }, update: function (e, t, a) {
                    t.paths.transition().duration(a).style("opacity", 1).attr("d", d3.svg.line().x(function (t) {
                        return e.xScale(t.x) + e.xScale.rangeBand() / 2
                    }).y(function (t) {
                        return e.yScale(t.y)
                    }))
                }, exit: function (e, t, a) {
                    t.paths.exit().transition().duration(a).style("opacity", 0)
                }, destroy: function (e, t, a) {
                    t.paths.transition().duration(a).style("opacity", 0).remove()
                }};
            xChart.setVis("error", t);
            var a = [{xScale: "ordinal", yScale: "linear", main: [{className: ".errorExample", data: [{x: "Ponies", y: 12}, {x: "Unicorns", y: 23}, {x: "Trolls", y: 1}]}], comp: [{type: "error", className: ".comp.errorBar", data: [{x: "Ponies", y: 12, e: 5}, {x: "Unicorns", y: 23, e: 2}, {x: "Trolls", y: 1, e: 1}]}]}, {xScale: "ordinal", yScale: "linear", main: [{className: ".errorExample", data: [{x: "Ponies", y: 76}, {x: "Unicorns", y: 45}, {x: "Trolls", y: 82}]}], comp: [{type: "error", className: ".comp.errorBar", data: [{x: "Ponies", y: 76, e: 12}, {x: "Unicorns", y: 45, e: 3}, {x: "Trolls", y: 82, e: 12}]}]}], i = new xChart("bar", a[0], "#chart6"), o = 0;
            e()
        }
        e(), t(), a(), i(), o(), n()
    }
	
	, K = function () {
        window.onload = function () {
            var e = new JustGage({id: "g1", value: getRandomInt(0, 100), min: 0, max: 100, title: "Custom Width", label: "", gaugeWidthScale: .2}), t = new JustGage({id: "g2", value: getRandomInt(0, 100), min: 0, max: 100, title: "Custom Shadow", label: "", shadowOpacity: 1, shadowSize: 0, shadowVerticalOffset: 4}), a = new JustGage({id: "g3", value: getRandomInt(0, 100), min: 0, max: 100, title: "Custom Colors", label: "", levelColors: [Theme.colors.red, Theme.colors.yellow, Theme.colors.green]}), i = new JustGage({id: "g4", value: getRandomInt(0, 100), min: 0, max: 100, title: "Hide Labels", showMinMax: !1}), o = new JustGage({id: "g5", value: getRandomInt(0, 100), min: 0, max: 100, title: "Animation Type", label: "", startAnimationTime: 2e3, startAnimationType: ">", refreshAnimationTime: 1e3, refreshAnimationType: "bounce"}), n = new JustGage({id: "g6", value: getRandomInt(0, 100), min: 0, max: 100, title: "Minimal", label: "", showMinMax: !1, gaugeColor: "#E6E6E6", levelColors: ["#555555"], showInnerShadow: !1, startAnimationTime: 1, startAnimationType: "linear", refreshAnimationTime: 1, refreshAnimationType: "linear"});
            setInterval(function () {
                e.refresh(getRandomInt(0, 100)), t.refresh(getRandomInt(0, 100)), a.refresh(getRandomInt(0, 100)), i.refresh(getRandomInt(0, 100)), o.refresh(getRandomInt(0, 100)), n.refresh(getRandomInt(0, 100))
            }, 2500)
        }
    }
	
	, Z = function () {
        $("#pie_1").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a))
            }, lineWidth: 3, barColor: "#A8BC7B"});
        var e = window.chart = $("#pie_1").data("easyPieChart");
        $("#js_update_1").on("click", function () {
            e.update(100 * Math.random())
        }), $("#pie_2").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a))
            }, lineWidth: 6, barColor: "#F0AD4E"});
        var t = window.chart = $("#pie_2").data("easyPieChart");
        $("#js_update_2").on("click", function () {
            t.update(100 * Math.random())
        }), $("#pie_3").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a))
            }, lineWidth: 9, barColor: "#D9534F"});
        var a = window.chart = $("#pie_3").data("easyPieChart");
        $("#js_update_3").on("click", function () {
            a.update(100 * Math.random())
        }), $("#pie_4").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a))
            }, lineWidth: 12, barColor: "#70AFC4", lineCap: "butt"});
        var i = window.chart = $("#pie_4").data("easyPieChart");
        $("#js_update_4").on("click", function () {
            i.update(100 * Math.random())
        })
    }
	
	, et = function () {
        $("#pie_1").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a) + "%")
            }, lineWidth: 6, barColor: "#F0AD4E"});
        window.chart = $("#pie_1").data("easyPieChart");
        $("#pie_2").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a) + "%")
            }, lineWidth: 6, barColor: "#D9534F"});
        window.chart = $("#pie_2").data("easyPieChart");
        $("#pie_3").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a) + "%")
            }, lineWidth: 6, barColor: "#70AFC4"});
        window.chart = $("#pie_3").data("easyPieChart")
    }
	
	, tt = function () {
        $(".sparkline").each(function () {
            var e, t, a, i;
            return a = $(this).attr("data-color") || "red", i = "18px", $(this).hasClass("big") && (t = "5px", e = "2px", i = "40px"), $(this).sparkline("html", {type: "bar", barColor: Theme.colors[a], height: i, barWidth: t, barSpacing: e, zeroAxis: !1})
        }), $(".sparklinepie").each(function () {
            var e;
            return e = "50px", $(this).hasClass("big") && (e = "70px"), $(this).sparkline("html", {type: "pie", height: e, sliceColors: [Theme.colors.blue, Theme.colors.red, Theme.colors.green, Theme.colors.orange]})
        }), $(".linechart").each(function () {
            var e;
            return e = "18px", $(this).hasClass("linechart-lg") && (e = "30px"), $(this).sparkline("html", {type: "line", height: e, width: "150px", minSpotColor: Theme.colors.red, maxSpotColor: Theme.colors.green, spotRadius: 3, lineColor: Theme.colors.primary, fillColor: "rgba(94,135,176,0.1)", lineWidth: 1.2, highlightLineColor: Theme.colors.red, highlightSpotColor: Theme.colors.yellow})
        })
    }
	
	, at = function () {
        var e = function (e) {
            var t = {title: $.trim(e.text())};
            e.data("eventObject", t), e.draggable({zIndex: 999, revert: !0, revertDuration: 0})
        }, t = function (t) {
            t = 0 == t.length ? "Untitled Event" : t;
            var a = $('<div class="external-event">' + t + "</div>");
            jQuery("#event-box").append(a), e(a)
        };
        $("#external-events div.external-event").each(function () {
            e($(this))
        }), $("#add-event").unbind("click").click(function () {
            var e = $("#event-title").val();
            t(e)
        });
        var a = new Date, i = a.getDate(), o = a.getMonth(), n = a.getFullYear(), r = $("#calendar").fullCalendar({header: {left: "prev,next today", center: "title", right: "month,agendaWeek,agendaDay"}, selectable: !0, selectHelper: !0, select: function (e, t, a) {
                var i = prompt("Event Title:");
                i && r.fullCalendar("renderEvent", {title: i, start: e, end: t, allDay: a}, !0), r.fullCalendar("unselect")
            }, editable: !0, editable:!0, droppable: !0, drop: function (e, t) {
                var a = $(this).data("eventObject"), i = $.extend({}, a);
                i.start = e, i.allDay = t, $("#calendar").fullCalendar("renderEvent", i, !0), $("#drop-remove").is(":checked") && $(this).remove()
            }, events: [{title: "All Day Event", start: new Date(n, o, 1), backgroundColor: Theme.colors.blue}, {title: "Long Event", start: new Date(n, o, i - 5), end: new Date(n, o, i - 2), backgroundColor: Theme.colors.red}, {id: 999, title: "Repeating Event", start: new Date(n, o, i - 3, 16, 0), allDay: !1, backgroundColor: Theme.colors.yellow}, {id: 999, title: "Repeating Event", start: new Date(n, o, i + 4, 16, 0), allDay: !1, backgroundColor: Theme.colors.primary}, {title: "Meeting", start: new Date(n, o, i, 10, 30), allDay: !1, backgroundColor: Theme.colors.green}, {title: "Lunch", start: new Date(n, o, i, 12, 0), end: new Date(n, o, i, 14, 0), allDay: !1, backgroundColor: Theme.colors.red}, {title: "Birthday Party", start: new Date(n, o, i + 1, 19, 0), end: new Date(n, o, i + 1, 22, 30), allDay: !1, backgroundColor: Theme.colors.gray}, {title: "Click for Google", start: new Date(n, o, 28), end: new Date(n, o, 29), url: "http://google.com/", backgroundColor: Theme.colors.green}]})
    }
	
	, it = function () {
        var e = function (e) {
            var t = {map: "world_en", backgroundColor: null, borderColor: "#333333", borderOpacity: .5, borderWidth: 1, color: Theme.colors.blue, enableZoom: !0, hoverColor: Theme.colors.yellow, hoverOpacity: null, values: sample_data, normalizeFunction: "linear", scaleColors: ["#b6da93", "#427d1a"], selectedColor: "#c9dfaf", selectedRegion: null, showTooltip: !0, onRegionOver: function (e, t) {
                    "ca" == t && e.preventDefault()
                }, onRegionClick: function (e, t, a) {
                    var i = 'You clicked "' + a + '" which has the code: ' + t.toUpperCase();
                    alert(i)
                }};
            t.map = e + "_en";
            var a = jQuery("#vmap_" + e);
            a && (a.width(a.parent().width()), a.vectorMap(t))
        };
        e("world"), e("usa"), e("europe"), e("russia"), e("germany"), App.addResponsiveFunction(function () {
            e("world"), e("usa"), e("europe"), e("russia"), e("germany")
        })
    }
	
	, ot = function () {
        function e() {
            var e = $(window).width();
            768 > e ? $("#filter-items .item").addClass("width-100") : $("#filter-items .item").removeClass("width-100")
        }
        var t = $("#filter-items");
        t.imagesLoaded(function () {
            t.isotope({}), $("#filter-controls a").click(function () {
                var e = $(this).attr("data-filter");
                return t.isotope({filter: e}), !1
            }), $("#e1").change(function () {
                var e = $(this).find(":selected").val();
                return t.isotope({filter: e}), !1
            })
        }), e(), jQuery(window).resize(function () {
            e()
        })
    }
	
	, nt = function () {
        $(".filter-content").hover(function () {
            var e = $(this).children(".hover-content");
            e.removeClass("fadeOut").addClass("animated fadeIn").show()
        }, function () {
            var e = $(this).children(".hover-content");
            e.removeClass("fadeIn").addClass("fadeOut")
        })
    }
	
	, rt = function () {
        function e() {
            t && clearTimeout(t), t = setTimeout(function () {
                var e = 442, t = .95;
                jQuery("#cboxOverlay").is(":visible") && ($.colorbox.resize({width: $(window).width() > e + 20 ? e : Math.round($(window).width() * t)}), $(".cboxPhoto").css({width: $("#cboxLoadedContent").innerWidth(), height: "auto"}), $("#cboxLoadedContent").height($(".cboxPhoto").height()), $.colorbox.resize())
            }, 300)
        }
        $(".colorbox-button").colorbox({rel: "colorbox-button", maxWidth: "95%", maxHeight: "95%"});
        var t;
        jQuery(window).resize(e), window.addEventListener("orientationchange", e, !1)
    }
	
	, st = function () {
        $.backstretch(["img/login/1.jpg", "img/login/2.jpg", "img/login/3.jpg", "img/login/4.jpg"], {duration: 3e3, fade: 750})
    }
	
	, lt = function (e) {
        var t = function () {
            var t = $("." + e + " .chat-form input"), a = t.val();
            if (0 != a.length) {
                var i = moment().format("YYYY-MM-DD HH:mm:ss"), o = "";
                o += '<li class="animated fadeInLeft media">', o += '<a class="pull-right" href="#">', o += '<img class="media-object" alt="Generic placeholder image" src="img/chat/headshot2.jpg">', o += "</a>", o += '<div class="pull-right media-body chat-pop mod">', o += '<h4 class="media-heading">You <span class="pull-left"><abbr id="curr-time" class="timeago" title="' + i + '" >' + i + '</abbr> <i class="fa fa-clock-o"></i></span></h4>', o += a, o += "</div>", o += "</li>";
                var n = $("." + e + " .chat-list");
                n.append(o), jQuery("abbr.timeago").timeago(), t.val(""), $("." + e + " .scroller").slimScroll({scrollTo: n.height()})
            }
        };
        $("." + e + " .chat-form .btn").click(function (e) {
            e.preventDefault(), t()
        });
        var a = $("." + e + " .chat-form input");
        a.keypress(function (e) {
            return 13 == e.which ? (t(), !1) : void 0
        })
    }
	
	, ct = function () {
        createStoryJS({type: "timeline", width: "100%", height: "600", source: "js/timelinejs/example_json.json", embed_id: "my-timeline", debug: !0, css: "js/timelinejs/css/timeline.css", js: "js/timelinejs/js/timeline-min.js"})
    }
	
	, dt = function () {
        $("#address-book").sliderNav(), $("#address-book .slider-content ul li ul li a").click(function (e) {
            e.preventDefault();
            var t = $("#contact-card"), a = $(this).text();
            $("#contact-card .panel-title").html(a), $("#contact-card #card-name").html(a);
            var i = Math.floor(5 * Math.random()) + 1;
            $("#contact-card .headshot img").attr("src", "img/addressbook/" + i + ".jpg"), t.removeClass("animated fadeInUp").addClass("animated fadeInUp");
            window.setTimeout(function () {
                t.removeClass("animated fadeInUp")
            }, 1300)
        })
    }
	
	, mt = function () {
        $("#list-toggle .list-group a").click(function () {
            $("#list-toggle .list-group > a.active").removeClass("active"), $(this).addClass("active")
        })
    }
	
	, ut = function () {
        $(".box-container").sortable({connectWith: ".box-container", items: "> .box", opacity: .8, revert: !0, forceHelperSize: !0, placeholder: "box-placeholder", forcePlaceholderSize: !0, tolerance: "pointer"})
    }
	
	, ht = function () {
        $(".footer-tools").on("click", ".go-top", function (e) {
            App.scrollTo(), e.preventDefault()
        })
    }
	
	, pt = function () {
        a && o && $("#main-content").addClass("margin-top-100"), !a && o && $("#main-content").removeClass("margin-top-100").addClass("margin-top-50")
    }
	
	, ft = function () {
        function e() {
            function e(e, t, a) {
                $('<div id="tooltip">' + a + "</div>").css({position: "absolute", display: "none", top: t + 5, left: e + 15, border: "1px solid #333", padding: "4px", color: "#fff", "border-radius": "3px", "background-color": "#333", opacity: .8}).appendTo("body").fadeIn(200)
            }
            // var t = [[0, 1.5], [1, 2], [2, 1], [3, 1.5], [4, 2.5], [5, 2], [6, 2], [7, .5], [8, 1], [9, 1.5], [10, 2], [11, 2.5], [12, 2], [13, 1.5], [14, 2.8], [15, 2], [16, 3], [17, 2], [18, 2.5], [19, 3], [20, 2.5], [21, 2], [22, 1.5], [23, 2.5], [24, 2], [25, 1.5], [26, 1], [27, .5], [28, 1], [29, 1], [30, 1.5], [31, 1]], a = [[0, 2.5], [1, 3.5], [2, 2], [3, 3], [4, 4], [5, 3.5], [6, 3.5], [7, 1], [8, 2], [9, 3], [10, 4], [11, 5], [12, 4], [13, 3], [14, 5], [15, 3.5], [16, 5], [17, 4], [18, 5], [19, 6], [20, 5], [21, 4], [22, 3], [23, 5], [24, 4], [25, 3], [26, 2], [27, 1], [28, 2], [29, 2], [30, 3], [31, 2]], i = ($.plot($("#chart-dash"), [{data: a, label: "Pageviews", bars: {show: !0, fill: !0, barWidth: .4, align: "center", lineWidth: 13}}, {data: t, label: "Visits", lines: {show: !0, lineWidth: 2}, points: {show: !0, lineWidth: 2, fill: !0}, shadowSize: 0}, {data: t, label: "Visits", lines: {show: !0, lineWidth: 1, fill: !0, fillColor: {colors: [{opacity: .05}, {opacity: .01}]}}, points: {show: !0, lineWidth: .5, fill: !0}, shadowSize: 0}], {grid: {hoverable: !0, clickable: !0, tickColor: "#f7f7f7", borderWidth: 0, labelMargin: 10, margin: {top: 0, left: 5, bottom: 0, right: 0}}, legend: {show: !1}, colors: ["rgba(109,173,189,0.5)", "#70AFC4", "#DB5E8C"], xaxis: {ticks: 5, tickDecimals: 0, tickColor: "#fff"}, yaxis: {ticks: 3, tickDecimals: 0}}), null);
            $("#chart-dash").bind("plothover", function (t, a, o) {
                if ($("#x").text(a.x.toFixed(2)), $("#y").text(a.y.toFixed(2)), o) {
                    if (i != o.dataIndex) {
                        i = o.dataIndex, $("#tooltip").remove();
                        var n = o.datapoint[0].toFixed(2), r = o.datapoint[1].toFixed(2);
                        e(o.pageX, o.pageY, o.series.label + " of " + n + " = " + r)
                    }
                } else
                    $("#tooltip").remove(), i = null
            })
        }
        function t() {
            function e(e, t) {
                for (var a = [], i = 0; 100 >= i; ++i) {
                    var o = e + i * (t - e) / 100;
                    a.push([o, Math.cos(o * Math.sin(o))])
                }
                return[{label: "cos(x sin(x))", data: a}]
            }
            // var t = {grid: {hoverable: !0, clickable: !0, tickColor: "#f7f7f7", borderWidth: 0, labelMargin: 10, margin: {top: 0, left: 5, bottom: 0, right: 0}}, legend: {show: !1}, series: {lines: {show: !0}, shadowSize: 0, points: {show: !0}}, colors: ["#D9534F"], yaxis: {ticks: 10}, selection: {mode: "xy", color: "#F1ADAC"}}, a = e(0, 3 * Math.PI), i = $.plot("#placeholder", a, t), o = $.plot($("#overview"), a, {legend: {show: !1}, series: {lines: {show: !0, lineWidth: 1}, shadowSize: 0}, xaxis: {ticks: 4}, yaxis: {ticks: 3, min: -2, max: 2}, colors: ["#D9534F"], grid: {color: "#999", borderWidth: 0}, selection: {mode: "xy", color: "#F1ADAC"}});
            $("#placeholder").bind("plotselected", function (a, n) {
                n.xaxis.to - n.xaxis.from < 1e-5 && (n.xaxis.to = n.xaxis.from + 1e-5), n.yaxis.to - n.yaxis.from < 1e-5 && (n.yaxis.to = n.yaxis.from + 1e-5), i = $.plot("#placeholder", e(n.xaxis.from, n.xaxis.to), $.extend(!0, {}, t, {xaxis: {min: n.xaxis.from, max: n.xaxis.to}, yaxis: {min: n.yaxis.from, max: n.yaxis.to}})), o.setSelection(n, !0)
            }), $("#overview").bind("plotselected", function (e, t) {
                i.setSelection(t)
            }), $("#footer").prepend("Flot " + $.plot.version + " &ndash; ")
        }
        function a() {
            function e(e, t, a) {
                $('<div id="tooltip">' + a + "</div>").css({position: "absolute", display: "none", top: t + 5, left: e + 5, border: "1px solid #fdd", padding: "2px", "background-color": "#dfeffc", opacity: .8}).appendTo("body").fadeIn(200)
            }
            // var t = [[1, 100 * Math.random()], [2, 100 * Math.random()], [3, 100 * Math.random()], [4, 100 * Math.random()], [5, 100 * Math.random()], [6, 100 * Math.random()], [7, 100 * Math.random()], [8, 100 * Math.random()], [9, 100 * Math.random()], [10, 100 * Math.random()], [11, 100 * Math.random()], [12, 100 * Math.random()]], a = $(this).parent().parent().css("color"), i = ($.plot($("#chart-revenue"), [{data: t}], {series: {label: "Revenue", lines: {show: !0, lineWidth: 3, fill: !1}, points: {show: !0, lineWidth: 3, fill: !0, fillColor: a}, shadowSize: 0}, grid: {hoverable: !0, clickable: !0, tickColor: "rgba(255,255,255,.15)", borderColor: "rgba(255,255,255,0)"}, colors: ["#fff"], xaxis: {font: {color: "#fff"}, ticks: 6, tickDecimals: 0, tickColor: a}, yaxis: {font: {color: "#fff"}, ticks: 4, tickDecimals: 0, autoscaleMargin: 1e-6}, legend: {show: !1}}), null);
            $("#chart-revenue").bind("plothover", function (t, a, o) {
                if ($("#x").text(a.x.toFixed(2)), $("#y").text(a.y.toFixed(2)), o) {
                    if (i != o.dataIndex) {
                        i = o.dataIndex, $("#tooltip").remove();
                        var n = o.datapoint[0].toFixed(2), r = o.datapoint[1].toFixed(2);
                        e(o.pageX, o.pageY, o.series.label + " on " + n + " = " + r)
                    }
                } else
                    $("#tooltip").remove(), i = null
            })
        }
        e(), t(), a(), $("#dash_pie_1").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a) + "%")
            }, lineWidth: 6, barColor: Theme.colors.purple});
        var i = window.chart = $("#dash_pie_1").data("easyPieChart");
        $("#dash_pie_2").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a) + "%")
            }, lineWidth: 6, barColor: Theme.colors.yellow});
        var o = window.chart = $("#dash_pie_2").data("easyPieChart");
        $("#dash_pie_3").easyPieChart({easing: "easeOutBounce", onStep: function (e, t, a) {
                $(this.el).find(".percent").text(Math.round(a) + "%")
            }, lineWidth: 6, barColor: Theme.colors.pink});
        var n = window.chart = $("#dash_pie_3").data("easyPieChart");
        $(".js_update").on("click", function () {
            i.update(100 * Math.random()), o.update(100 * Math.random()), n.update(100 * Math.random()), a()
        })
    }
	
	, gt = function () {
        var e = function (e) {
            $("#skin-switcher").attr("href", "css/themes/" + e + ".css"), $.cookie("skin_color", e)
        };
        $("ul.skins > li a").click(function () {
            var t = $(this).data("skin");
            e(t)
        }), $.cookie("skin_color") && e($.cookie("skin_color"))
    }
	
	, bt = function () {
        /*$.cookie("gritter_show") || ($.cookie("gritter_show", 1), setTimeout(function () {
            var e = $.gritter.add({title: "Welcome to Cloud Admin!", text: "Cloud is a feature-rich Responsive Admin Dashboard Template with a wide array of plugins!", image: "img/gritter/cloud.png", sticky: !0, time: "", class_name: "my-sticky-class"});
            setTimeout(function () {
                $.gritter.remove(e, {fade: !0, speed: "slow"})
            }, 12e3)
        }, 2e3), setTimeout(function () {
            var e = $.gritter.add({title: "Customize Cloud Admin!", text: "Cloud Admin is easily customizable, lightweight and has a great User Experience.", image: "img/gritter/settings.png", sticky: !0, time: "", class_name: "my-sticky-class"});
            setTimeout(function () {
                $.gritter.remove(e, {fade: !0, speed: "slow"})
            }, 13e3)
        }, 8e3), setTimeout(function () {
            $.extend($.gritter.options, {position: "top-left"});
            var e = $.gritter.add({position: "top-left", title: "Buy Cloud Admin!", text: "Purchase Cloud Admin theme and get access to future updates at no extra cost. Buy now!", image: "img/gritter/buy.png", sticky: !0, time: "", class_name: "my-sticky-class"});
            $.extend($.gritter.options, {position: "top-right"}), setTimeout(function () {
                $.gritter.remove(e, {fade: !0, speed: "slow"})
            }, 15e3)
        }, 15e3), setTimeout(function () {
            $.extend($.gritter.options, {position: "top-left"});
            var e = $.gritter.add({title: "Notification", text: "You have 6 new notifications.", sticky: !0, time: "", class_name: "my-sticky-class"});
            setTimeout(function () {
                $.gritter.remove(e, {fade: !0, speed: "slow"})
            }, 4e3), $.extend($.gritter.options, {position: "top-right"})
        }, 2e4), setTimeout(function () {
            $.extend($.gritter.options, {position: "top-left"});
            var e = $.gritter.add({title: "Inbox", text: "You have 5 new messages in your inbox.", sticky: !0, time: "", class_name: "my-sticky-class"});
            $.extend($.gritter.options, {position: "top-right"}), setTimeout(function () {
                $.gritter.remove(e, {fade: !0, speed: "slow"})
            }, 4e3)
        }, 25e3))*/
    }
	
	, vt = function () {
        $(".datepicker").datepicker()
    };
    return{init: function () {
            App.isPage("index") && (g(), tt(), ft(), lt("chat-window"), at(), bt()), App.isPage("widgets_box") && ut(), App.isPage("elements") && (C(), S(), D(), M(), A()), App.isPage("button_icons") && (Q(), P()), App.isPage("sliders_progress") && (z(), I(), F()), App.isPage("treeview") && O(), App.isPage("nestable_lists") && N(), App.isPage("simple_table") && E(), App.isPage("dynamic_table") && B(), App.isPage("jqgrid_plugin") && Y(), App.isPage("forms") && (R(), L(), U(), H(), q(), A()), App.isPage("rich_text_editors") && G(), App.isPage("dropzone_file_upload") && V(), App.isPage("xcharts") && X(), App.isPage("others") && (K(), Z(), tt()), App.isPage("calendar") && (at(), q()), App.isPage("vector_maps") && it(), App.isPage("gallery") && (ot(), nt(), rt()), App.isPage("login") && q(), App.isPage("wizards_validations") && q(), App.isPage("login_bg") && (q(), st()), App.isPage("chats") && (lt("chat-window"), lt("chat-widget"), _()), App.isPage("todo_timeline") && ct(), App.isPage("address_book") && dt(), App.isPage("orders") && _(), App.isPage("faq") && mt(), App.isPage("user_profile") && (et(), tt(), q(), vt()), App.isPage("mini_sidebar") && m(), App.isPage("fixed_header_sidebar") && f(), l(), d(), h(), c(), u(), b(), y(), x(), w(), k(), T(), j(), W(), ht(), pt(), gt()
        }, setPage: function (t) {
            e = t
        }, isPage: function (t) {
            return e == t ? !0 : !1
        }, addResponsiveFunction: function (e) {
            n.push(e)
        }, scrollTo: function (e, t) {
            pos = e && e.size() > 0 ? e.offset().top : 0, jQuery("html,body").animate({scrollTop: pos + (t ? t : 0)}, "slow")
        }, scrollTop: function () {
            App.scrollTo()
        }, initUniform: function (e) {
            e ? jQuery(e).each(function () {
                0 == $(this).parents(".checker").size() && ($(this).show(), $(this).uniform())
            }) : J()
        }, blockUI: function (e) {
            lastBlockedUI = e, jQuery(e).block({message: '<img src="./img/loaders/12.gif" align="absmiddle">', css: {border: "none", padding: "2px", backgroundColor: "none"}, overlayCSS: {backgroundColor: "#000", opacity: .05, cursor: "wait"}})
        }, unblockUI: function (e) {
            jQuery(e).unblock({onUnblock: function () {
                    jQuery(e).removeAttr("style")
                }})
        }}
}();


!function (e) {
    e.fn.admin_tree = function (t) {
        var a = {"open-icon": "fa fa-folder-open", "close-icon": "fa fa-folder", selectable: !0, "selected-icon": "fa fa-check", "unselected-icon": "tree-dot"};
        return a = e.extend({}, a, t), this.each(function () {
            var t = e(this);
            t.html('<div class = "tree-folder" style="display:none;">				<div class="tree-folder-header">					<i class="' + a["close-icon"] + '"></i>					<div class="tree-folder-name"></div>				</div>				<div class="tree-folder-content"></div>				<div class="tree-loader" style="display:none"></div>			</div>			<div class="tree-item" style="display:none;">				' + (null == a["unselected-icon"] ? "" : '<i class="' + a["unselected-icon"] + '"></i>') + '				<div class="tree-item-name"></div>			</div>'), t.addClass(1 == a.selectable ? "tree-selectable" : "tree-unselectable"), t.tree(a)
        }), this
    }
}(window.jQuery), function () {
    this.Theme = function () {
        function e() {
        }
        return e.colors = {white: "#FFFFFF", primary: "#5E87B0", red: "#D9534F", green: "#A8BC7B", blue: "#70AFC4", orange: "#F0AD4E", yellow: "#FCD76A", gray: "#6B787F", lightBlue: "#D4E5DE", purple: "#A696CE", pink: "#DB5E8C", dark_orange: "#F38630"}, e
    }()
}(window.jQuery);