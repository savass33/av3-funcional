// Compiled by ClojureScript 1.10.844 {}
goog.provide('trab_av3.core');
goog.require('cljs.core');
goog.require('reagent.dom');
goog.require('trab_av3.ui.componentes.app');
trab_av3.core.init = (function trab_av3$core$init(){
trab_av3.ui.componentes.app.atualizar_dados_BANG_.call(null);

return reagent.dom.render.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [trab_av3.ui.componentes.app.painel_principal], null),document.getElementById("app"));
});
goog.exportSymbol('trab_av3.core.init', trab_av3.core.init);

//# sourceMappingURL=core.js.map
