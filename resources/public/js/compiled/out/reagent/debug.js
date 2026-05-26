// Compiled by ClojureScript 1.10.844 {}
goog.provide('reagent.debug');
goog.require('cljs.core');
reagent.debug.has_console = (typeof console !== 'undefined');
reagent.debug.tracking = false;
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.warnings !== 'undefined')){
} else {
reagent.debug.warnings = cljs.core.atom.call(null,null);
}
if((typeof reagent !== 'undefined') && (typeof reagent.debug !== 'undefined') && (typeof reagent.debug.track_console !== 'undefined')){
} else {
reagent.debug.track_console = (function (){var o = ({});
(o.warn = (function() { 
var G__1476__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"warn","warn",-436710552)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__1476 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__1477__i = 0, G__1477__a = new Array(arguments.length -  0);
while (G__1477__i < G__1477__a.length) {G__1477__a[G__1477__i] = arguments[G__1477__i + 0]; ++G__1477__i;}
  args = new cljs.core.IndexedSeq(G__1477__a,0,null);
} 
return G__1476__delegate.call(this,args);};
G__1476.cljs$lang$maxFixedArity = 0;
G__1476.cljs$lang$applyTo = (function (arglist__1478){
var args = cljs.core.seq(arglist__1478);
return G__1476__delegate(args);
});
G__1476.cljs$core$IFn$_invoke$arity$variadic = G__1476__delegate;
return G__1476;
})()
);

(o.error = (function() { 
var G__1479__delegate = function (args){
return cljs.core.swap_BANG_.call(null,reagent.debug.warnings,cljs.core.update_in,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"error","error",-978969032)], null),cljs.core.conj,cljs.core.apply.call(null,cljs.core.str,args));
};
var G__1479 = function (var_args){
var args = null;
if (arguments.length > 0) {
var G__1480__i = 0, G__1480__a = new Array(arguments.length -  0);
while (G__1480__i < G__1480__a.length) {G__1480__a[G__1480__i] = arguments[G__1480__i + 0]; ++G__1480__i;}
  args = new cljs.core.IndexedSeq(G__1480__a,0,null);
} 
return G__1479__delegate.call(this,args);};
G__1479.cljs$lang$maxFixedArity = 0;
G__1479.cljs$lang$applyTo = (function (arglist__1481){
var args = cljs.core.seq(arglist__1481);
return G__1479__delegate(args);
});
G__1479.cljs$core$IFn$_invoke$arity$variadic = G__1479__delegate;
return G__1479;
})()
);

return o;
})();
}
reagent.debug.track_warnings = (function reagent$debug$track_warnings(f){
(reagent.debug.tracking = true);

cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

f.call(null);

var warns = cljs.core.deref.call(null,reagent.debug.warnings);
cljs.core.reset_BANG_.call(null,reagent.debug.warnings,null);

(reagent.debug.tracking = false);

return warns;
});

//# sourceMappingURL=debug.js.map
