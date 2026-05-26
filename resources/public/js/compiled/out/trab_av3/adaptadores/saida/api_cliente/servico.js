// Compiled by ClojureScript 1.10.844 {}
goog.provide('trab_av3.adaptadores.saida.api_cliente.servico');
goog.require('cljs.core');
goog.require('ajax.core');
trab_av3.adaptadores.saida.api_cliente.servico.api_url = "http://localhost:3000/api";
trab_av3.adaptadores.saida.api_cliente.servico.registrar_usuario_BANG_ = (function trab_av3$adaptadores$saida$api_cliente$servico$registrar_usuario_BANG_(dados,callback_sucesso){
return ajax.core.POST.call(null,[trab_av3.adaptadores.saida.api_cliente.servico.api_url,"/usuario"].join(''),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"params","params",710516235),dados,new cljs.core.Keyword(null,"format","format",-1306924766),new cljs.core.Keyword(null,"json","json",1279968570),new cljs.core.Keyword(null,"handler","handler",-195596612),callback_sucesso], null));
});
trab_av3.adaptadores.saida.api_cliente.servico.registrar_refeicao_BANG_ = (function trab_av3$adaptadores$saida$api_cliente$servico$registrar_refeicao_BANG_(dados,callback_sucesso){
return ajax.core.POST.call(null,[trab_av3.adaptadores.saida.api_cliente.servico.api_url,"/refeicao"].join(''),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"params","params",710516235),dados,new cljs.core.Keyword(null,"format","format",-1306924766),new cljs.core.Keyword(null,"json","json",1279968570),new cljs.core.Keyword(null,"handler","handler",-195596612),callback_sucesso], null));
});
trab_av3.adaptadores.saida.api_cliente.servico.registrar_exercicio_BANG_ = (function trab_av3$adaptadores$saida$api_cliente$servico$registrar_exercicio_BANG_(dados,callback_sucesso){
return ajax.core.POST.call(null,[trab_av3.adaptadores.saida.api_cliente.servico.api_url,"/exercicio"].join(''),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"params","params",710516235),dados,new cljs.core.Keyword(null,"format","format",-1306924766),new cljs.core.Keyword(null,"json","json",1279968570),new cljs.core.Keyword(null,"handler","handler",-195596612),callback_sucesso], null));
});
trab_av3.adaptadores.saida.api_cliente.servico.obter_extrato_BANG_ = (function trab_av3$adaptadores$saida$api_cliente$servico$obter_extrato_BANG_(callback_sucesso){
return ajax.core.GET.call(null,[trab_av3.adaptadores.saida.api_cliente.servico.api_url,"/extrato"].join(''),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"response-format","response-format",1664465322),new cljs.core.Keyword(null,"json","json",1279968570),new cljs.core.Keyword(null,"keywords?","keywords?",764949733),true,new cljs.core.Keyword(null,"handler","handler",-195596612),callback_sucesso], null));
});
trab_av3.adaptadores.saida.api_cliente.servico.obter_saldo_BANG_ = (function trab_av3$adaptadores$saida$api_cliente$servico$obter_saldo_BANG_(callback_sucesso){
return ajax.core.GET.call(null,[trab_av3.adaptadores.saida.api_cliente.servico.api_url,"/saldo"].join(''),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"response-format","response-format",1664465322),new cljs.core.Keyword(null,"json","json",1279968570),new cljs.core.Keyword(null,"keywords?","keywords?",764949733),true,new cljs.core.Keyword(null,"handler","handler",-195596612),callback_sucesso], null));
});

//# sourceMappingURL=servico.js.map
