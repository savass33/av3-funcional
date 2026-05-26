// Compiled by ClojureScript 1.10.844 {}
goog.provide('ajax.xml_http_request');
goog.require('cljs.core');
goog.require('ajax.protocols');
goog.require('goog.string');
ajax.xml_http_request.ready_state = (function ajax$xml_http_request$ready_state(e){
return new cljs.core.PersistentArrayMap(null, 5, [(0),new cljs.core.Keyword(null,"not-initialized","not-initialized",-1937378906),(1),new cljs.core.Keyword(null,"connection-established","connection-established",-1403749733),(2),new cljs.core.Keyword(null,"request-received","request-received",2110590540),(3),new cljs.core.Keyword(null,"processing-request","processing-request",-264947221),(4),new cljs.core.Keyword(null,"response-ready","response-ready",245208276)], null).call(null,e.target.readyState);
});
ajax.xml_http_request.append = (function ajax$xml_http_request$append(current,next){
if(cljs.core.truth_(current)){
return [cljs.core.str.cljs$core$IFn$_invoke$arity$1(current),", ",cljs.core.str.cljs$core$IFn$_invoke$arity$1(next)].join('');
} else {
return next;
}
});
ajax.xml_http_request.process_headers = (function ajax$xml_http_request$process_headers(header_str){
if(cljs.core.truth_(header_str)){
return cljs.core.reduce.call(null,(function (headers,header_line){
if(goog.string.isEmptyOrWhitespace(header_line)){
return headers;
} else {
var key_value = goog.string.splitLimit(header_line,": ",(2));
return cljs.core.update.call(null,headers,(key_value[(0)]),ajax.xml_http_request.append,(key_value[(1)]));
}
}),cljs.core.PersistentArrayMap.EMPTY,header_str.split("\r\n"));
} else {
return cljs.core.PersistentArrayMap.EMPTY;
}
});
ajax.xml_http_request.xmlhttprequest = (((typeof goog !== 'undefined') && (typeof goog.global !== 'undefined') && (typeof goog.global.XMLHttpRequest !== 'undefined'))?goog.global.XMLHttpRequest:(((typeof require !== 'undefined'))?(function (){var req = require;
return req.call(null,"xmlhttprequest").XMLHttpRequest;
})():null));
(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxImpl$ = cljs.core.PROTOCOL_SENTINEL);

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxImpl$_js_ajax_request$arity$3 = (function (this$,p__2214,handler){
var map__2215 = p__2214;
var map__2215__$1 = cljs.core.__destructure_map.call(null,map__2215);
var uri = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"uri","uri",-774711847));
var method = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"method","method",55703592));
var body = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"body","body",-2049205669));
var headers = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"headers","headers",-835030129));
var timeout = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"timeout","timeout",-318625318),(0));
var with_credentials = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"with-credentials","with-credentials",-1163127235),false);
var response_format = cljs.core.get.call(null,map__2215__$1,new cljs.core.Keyword(null,"response-format","response-format",1664465322));
var this$__$1 = this;
(this$__$1.withCredentials = with_credentials);

(this$__$1.onreadystatechange = (function (p1__2213_SHARP_){
if(cljs.core._EQ_.call(null,new cljs.core.Keyword(null,"response-ready","response-ready",245208276),ajax.xml_http_request.ready_state.call(null,p1__2213_SHARP_))){
return handler.call(null,this$__$1);
} else {
return null;
}
}));

this$__$1.open(method,uri,true);

(this$__$1.timeout = timeout);

var temp__5753__auto___2232 = new cljs.core.Keyword(null,"type","type",1174270348).cljs$core$IFn$_invoke$arity$1(response_format);
if(cljs.core.truth_(temp__5753__auto___2232)){
var response_type_2233 = temp__5753__auto___2232;
(this$__$1.responseType = cljs.core.name.call(null,response_type_2233));
} else {
}

var seq__2216_2234 = cljs.core.seq.call(null,headers);
var chunk__2217_2235 = null;
var count__2218_2236 = (0);
var i__2219_2237 = (0);
while(true){
if((i__2219_2237 < count__2218_2236)){
var vec__2226_2238 = cljs.core._nth.call(null,chunk__2217_2235,i__2219_2237);
var k_2239 = cljs.core.nth.call(null,vec__2226_2238,(0),null);
var v_2240 = cljs.core.nth.call(null,vec__2226_2238,(1),null);
this$__$1.setRequestHeader(k_2239,v_2240);


var G__2241 = seq__2216_2234;
var G__2242 = chunk__2217_2235;
var G__2243 = count__2218_2236;
var G__2244 = (i__2219_2237 + (1));
seq__2216_2234 = G__2241;
chunk__2217_2235 = G__2242;
count__2218_2236 = G__2243;
i__2219_2237 = G__2244;
continue;
} else {
var temp__5753__auto___2245 = cljs.core.seq.call(null,seq__2216_2234);
if(temp__5753__auto___2245){
var seq__2216_2246__$1 = temp__5753__auto___2245;
if(cljs.core.chunked_seq_QMARK_.call(null,seq__2216_2246__$1)){
var c__4591__auto___2247 = cljs.core.chunk_first.call(null,seq__2216_2246__$1);
var G__2248 = cljs.core.chunk_rest.call(null,seq__2216_2246__$1);
var G__2249 = c__4591__auto___2247;
var G__2250 = cljs.core.count.call(null,c__4591__auto___2247);
var G__2251 = (0);
seq__2216_2234 = G__2248;
chunk__2217_2235 = G__2249;
count__2218_2236 = G__2250;
i__2219_2237 = G__2251;
continue;
} else {
var vec__2229_2252 = cljs.core.first.call(null,seq__2216_2246__$1);
var k_2253 = cljs.core.nth.call(null,vec__2229_2252,(0),null);
var v_2254 = cljs.core.nth.call(null,vec__2229_2252,(1),null);
this$__$1.setRequestHeader(k_2253,v_2254);


var G__2255 = cljs.core.next.call(null,seq__2216_2246__$1);
var G__2256 = null;
var G__2257 = (0);
var G__2258 = (0);
seq__2216_2234 = G__2255;
chunk__2217_2235 = G__2256;
count__2218_2236 = G__2257;
i__2219_2237 = G__2258;
continue;
}
} else {
}
}
break;
}

this$__$1.send((function (){var or__4160__auto__ = body;
if(cljs.core.truth_(or__4160__auto__)){
return or__4160__auto__;
} else {
return "";
}
})());

return this$__$1;
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxRequest$ = cljs.core.PROTOCOL_SENTINEL);

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxRequest$_abort$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1.abort();
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$ = cljs.core.PROTOCOL_SENTINEL);

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$_body$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1.response;
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$_status$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1.status;
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$_status_text$arity$1 = (function (this$){
var this$__$1 = this;
return this$__$1.statusText;
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$_get_all_headers$arity$1 = (function (this$){
var this$__$1 = this;
return ajax.xml_http_request.process_headers.call(null,this$__$1.getAllResponseHeaders());
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$_get_response_header$arity$2 = (function (this$,header){
var this$__$1 = this;
return this$__$1.getResponseHeader(header);
}));

(ajax.xml_http_request.xmlhttprequest.prototype.ajax$protocols$AjaxResponse$_was_aborted$arity$1 = (function (this$){
var this$__$1 = this;
return cljs.core._EQ_.call(null,(0),this$__$1.readyState);
}));

//# sourceMappingURL=xml_http_request.js.map
