// Compiled by ClojureScript 1.10.844 {}
goog.provide('trab_av3.portas.repositorio');
goog.require('cljs.core');

/**
 * Protocolo que abstrai a persistência de dados do sistema.
 * @interface
 */
trab_av3.portas.repositorio.Repositorio = function(){};

var trab_av3$portas$repositorio$Repositorio$salvar_usuario_BANG_$dyn_2587 = (function (this$,dados_usuario){
var x__4463__auto__ = (((this$ == null))?null:this$);
var m__4464__auto__ = (trab_av3.portas.repositorio.salvar_usuario_BANG_[goog.typeOf(x__4463__auto__)]);
if((!((m__4464__auto__ == null)))){
return m__4464__auto__.call(null,this$,dados_usuario);
} else {
var m__4461__auto__ = (trab_av3.portas.repositorio.salvar_usuario_BANG_["_"]);
if((!((m__4461__auto__ == null)))){
return m__4461__auto__.call(null,this$,dados_usuario);
} else {
throw cljs.core.missing_protocol.call(null,"Repositorio.salvar-usuario!",this$);
}
}
});
/**
 * Registra os dados pessoais do usuário.
 */
trab_av3.portas.repositorio.salvar_usuario_BANG_ = (function trab_av3$portas$repositorio$salvar_usuario_BANG_(this$,dados_usuario){
if((((!((this$ == null)))) && ((!((this$.trab_av3$portas$repositorio$Repositorio$salvar_usuario_BANG_$arity$2 == null)))))){
return this$.trab_av3$portas$repositorio$Repositorio$salvar_usuario_BANG_$arity$2(this$,dados_usuario);
} else {
return trab_av3$portas$repositorio$Repositorio$salvar_usuario_BANG_$dyn_2587.call(null,this$,dados_usuario);
}
});

var trab_av3$portas$repositorio$Repositorio$obter_usuario$dyn_2588 = (function (this$){
var x__4463__auto__ = (((this$ == null))?null:this$);
var m__4464__auto__ = (trab_av3.portas.repositorio.obter_usuario[goog.typeOf(x__4463__auto__)]);
if((!((m__4464__auto__ == null)))){
return m__4464__auto__.call(null,this$);
} else {
var m__4461__auto__ = (trab_av3.portas.repositorio.obter_usuario["_"]);
if((!((m__4461__auto__ == null)))){
return m__4461__auto__.call(null,this$);
} else {
throw cljs.core.missing_protocol.call(null,"Repositorio.obter-usuario",this$);
}
}
});
/**
 * Recupera os dados pessoais do usuário.
 */
trab_av3.portas.repositorio.obter_usuario = (function trab_av3$portas$repositorio$obter_usuario(this$){
if((((!((this$ == null)))) && ((!((this$.trab_av3$portas$repositorio$Repositorio$obter_usuario$arity$1 == null)))))){
return this$.trab_av3$portas$repositorio$Repositorio$obter_usuario$arity$1(this$);
} else {
return trab_av3$portas$repositorio$Repositorio$obter_usuario$dyn_2588.call(null,this$);
}
});

var trab_av3$portas$repositorio$Repositorio$adicionar_evento_BANG_$dyn_2589 = (function (this$,evento){
var x__4463__auto__ = (((this$ == null))?null:this$);
var m__4464__auto__ = (trab_av3.portas.repositorio.adicionar_evento_BANG_[goog.typeOf(x__4463__auto__)]);
if((!((m__4464__auto__ == null)))){
return m__4464__auto__.call(null,this$,evento);
} else {
var m__4461__auto__ = (trab_av3.portas.repositorio.adicionar_evento_BANG_["_"]);
if((!((m__4461__auto__ == null)))){
return m__4461__auto__.call(null,this$,evento);
} else {
throw cljs.core.missing_protocol.call(null,"Repositorio.adicionar-evento!",this$);
}
}
});
/**
 * Adiciona uma transação (refeição ou exercício) ao extrato.
 */
trab_av3.portas.repositorio.adicionar_evento_BANG_ = (function trab_av3$portas$repositorio$adicionar_evento_BANG_(this$,evento){
if((((!((this$ == null)))) && ((!((this$.trab_av3$portas$repositorio$Repositorio$adicionar_evento_BANG_$arity$2 == null)))))){
return this$.trab_av3$portas$repositorio$Repositorio$adicionar_evento_BANG_$arity$2(this$,evento);
} else {
return trab_av3$portas$repositorio$Repositorio$adicionar_evento_BANG_$dyn_2589.call(null,this$,evento);
}
});

var trab_av3$portas$repositorio$Repositorio$obter_extrato$dyn_2590 = (function (this$){
var x__4463__auto__ = (((this$ == null))?null:this$);
var m__4464__auto__ = (trab_av3.portas.repositorio.obter_extrato[goog.typeOf(x__4463__auto__)]);
if((!((m__4464__auto__ == null)))){
return m__4464__auto__.call(null,this$);
} else {
var m__4461__auto__ = (trab_av3.portas.repositorio.obter_extrato["_"]);
if((!((m__4461__auto__ == null)))){
return m__4461__auto__.call(null,this$);
} else {
throw cljs.core.missing_protocol.call(null,"Repositorio.obter-extrato",this$);
}
}
});
/**
 * Retorna a lista completa de transações registradas.
 */
trab_av3.portas.repositorio.obter_extrato = (function trab_av3$portas$repositorio$obter_extrato(this$){
if((((!((this$ == null)))) && ((!((this$.trab_av3$portas$repositorio$Repositorio$obter_extrato$arity$1 == null)))))){
return this$.trab_av3$portas$repositorio$Repositorio$obter_extrato$arity$1(this$);
} else {
return trab_av3$portas$repositorio$Repositorio$obter_extrato$dyn_2590.call(null,this$);
}
});


//# sourceMappingURL=repositorio.js.map
