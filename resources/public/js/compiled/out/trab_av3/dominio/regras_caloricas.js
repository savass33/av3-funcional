// Compiled by ClojureScript 1.10.844 {}
goog.provide('trab_av3.dominio.regras_caloricas');
goog.require('cljs.core');
trab_av3.dominio.regras_caloricas.fator_carboidrato = (4);
trab_av3.dominio.regras_caloricas.fator_proteina = (4);
trab_av3.dominio.regras_caloricas.fator_gordura = (9);
/**
 * Calcula o total de calorias de um alimento baseado em seus macronutrientes.
 * Fórmula: (carbo * 4) + (prot * 4) + (gord * 9).
 */
trab_av3.dominio.regras_caloricas.calcular_calorias_alimento = (function trab_av3$dominio$regras_caloricas$calcular_calorias_alimento(p__2526){
var map__2527 = p__2526;
var map__2527__$1 = cljs.core.__destructure_map.call(null,map__2527);
var carboidratos = cljs.core.get.call(null,map__2527__$1,new cljs.core.Keyword(null,"carboidratos","carboidratos",-2135089360),(0));
var proteina = cljs.core.get.call(null,map__2527__$1,new cljs.core.Keyword(null,"proteina","proteina",2086294072),(0));
var gordura = cljs.core.get.call(null,map__2527__$1,new cljs.core.Keyword(null,"gordura","gordura",1474047086),(0));
return (((carboidratos * trab_av3.dominio.regras_caloricas.fator_carboidrato) + (proteina * trab_av3.dominio.regras_caloricas.fator_proteina)) + (gordura * trab_av3.dominio.regras_caloricas.fator_gordura));
});
/**
 * Calcula a perda calórica de um exercício baseado na duração (minutos) e fator de intensidade.
 */
trab_av3.dominio.regras_caloricas.calcular_perda_exercicio = (function trab_av3$dominio$regras_caloricas$calcular_perda_exercicio(p__2528){
var map__2529 = p__2528;
var map__2529__$1 = cljs.core.__destructure_map.call(null,map__2529);
var duracao_minutos = cljs.core.get.call(null,map__2529__$1,new cljs.core.Keyword(null,"duracao-minutos","duracao-minutos",1493191651),(0));
var fator_intensidade = cljs.core.get.call(null,map__2529__$1,new cljs.core.Keyword(null,"fator-intensidade","fator-intensidade",2012898074),(0));
return (duracao_minutos * fator_intensidade);
});
/**
 * Adiciona um evento de refeição ao extrato.
 * Recebe o extrato atual (vetor) e os dados da refeição.
 * Retorna um novo vetor com o evento processado.
 */
trab_av3.dominio.regras_caloricas.processar_refeicao = (function trab_av3$dominio$regras_caloricas$processar_refeicao(extrato,refeicao){
var calorias = trab_av3.dominio.regras_caloricas.calcular_calorias_alimento.call(null,refeicao);
var evento = cljs.core.assoc.call(null,refeicao,new cljs.core.Keyword(null,"tipo","tipo",837631118),new cljs.core.Keyword(null,"refeicao","refeicao",1682202021),new cljs.core.Keyword(null,"calorias","calorias",-192122195),calorias);
return cljs.core.conj.call(null,extrato,evento);
});
/**
 * Adiciona um evento de exercício ao extrato.
 * Recebe o extrato atual (vetor) e os dados do exercício.
 * Retorna um novo vetor com o evento processado. As calorias são negativas.
 */
trab_av3.dominio.regras_caloricas.processar_exercicio = (function trab_av3$dominio$regras_caloricas$processar_exercicio(extrato,exercicio){
var calorias_gastas = trab_av3.dominio.regras_caloricas.calcular_perda_exercicio.call(null,exercicio);
var evento = cljs.core.assoc.call(null,exercicio,new cljs.core.Keyword(null,"tipo","tipo",837631118),new cljs.core.Keyword(null,"exercicio","exercicio",-1327795504),new cljs.core.Keyword(null,"calorias","calorias",-192122195),(- calorias_gastas));
return cljs.core.conj.call(null,extrato,evento);
});
/**
 * Calcula o saldo calórico final do dia.
 * Utiliza a função de ordem superior 'reduce' para somar as calorias de todos os eventos.
 * Saldo = (Soma das Calorias Ingeridas - Soma das Calorias Gastas) - Meta Biológica.
 */
trab_av3.dominio.regras_caloricas.consolidar_saldo_diario = (function trab_av3$dominio$regras_caloricas$consolidar_saldo_diario(extrato,meta_biologica){
var total_calorias = cljs.core.reduce.call(null,(function (acc,evento){
return (acc + new cljs.core.Keyword(null,"calorias","calorias",-192122195).cljs$core$IFn$_invoke$arity$1(evento));
}),(0),extrato);
return (total_calorias - meta_biologica);
});

//# sourceMappingURL=regras_caloricas.js.map
