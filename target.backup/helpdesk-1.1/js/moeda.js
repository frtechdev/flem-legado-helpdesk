/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * moeda
 *
 * @abstract Classe que formata de desformata valores monetários
 * em float e formata valores de float em moeda.
 *
 * @author anselmo
 *
 * @example
 * moeda.formatar(1000)
 * >> retornar 1.000,00
 * moeda.desformatar(1.000,00)
 * >> retornar 1000
 *
 * @version 1.0
 **/
var moeda = {
    /**
     * retiraFormatacao
     * Remove a formatação de uma string de moeda e retorna um float
     * @param {Object} num
     */
    desformatar: function(num){
        num = num.replace(".","");
        num = num.replace(",",".");
        return parseFloat(num);
    },
    /**
     * formatar
     * Deixar um valor float no formato monetário
     * @param {Object} num
     */
    formatar: function(num){
        x = 0;
        if(num<0){
            num = Math.abs(num);
            x = 1;
        }
        if( isNaN(num)) num = "0";
        cents = Math.floor((num*100+0.5)%100);
        num = Math.floor((num*100+0.5)/100).toString();
        if(cents < 10) cents = "0" + cents;
        for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
            num = num.substring(0,num.length-(4*i+3))+"."+num.substring(num.length-(4*i+3));
        ret = num + "," + cents;
        if (x == 1) ret = " - " + ret; return ret;
    },

    /**
     * arredondar
     * @abstract Arredonda um valor quebrado para duas casas
     * decimais.
     * @param {Object} num
     */
    arredondar: function(num){
        return Math.round(num*Math.pow(10,2))/Math.pow(10,2);
    }
}

// Formata o campo valor

function formataValor(campo) {
	campo.value = filtraCampo(campo);
	vr = campo.value;
	tam = vr.length;
	if ( tam <= 2 ){ 
 		campo.value = vr ; }
 	if ( (tam > 2) && (tam <= 5) ){
 		campo.value = vr.substr( 0, tam - 2 ) + ',' + vr.substr( tam - 2, tam ) ; }
 	if ( (tam >= 6) && (tam <= 8) ){
 		campo.value = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
 	if ( (tam >= 9) && (tam <= 11) ){
 		campo.value = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
 	if ( (tam >= 12) && (tam <= 14) ){
 		campo.value = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ; }
 	if ( (tam >= 15) && (tam <= 18) ){
 		campo.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ) ;}
}

// Formata o campo valor
function formataNumerico(campo) {
	campo.value = filtraCampo(campo);
	vr = campo.value;
	tam = vr.length;
}



// limpa todos os caracteres especiais do campo solicitado
function filtraCampo(campo){
	var s = "";
	var cp = "";
	vr = campo.value;
	tam = vr.length;
	for (i = 0; i < tam ; i++) {  
		if (vr.substring(i,i + 1) != "/" && vr.substring(i,i + 1) != "-" && vr.substring(i,i + 1) != "."  && vr.substring(i,i + 1) != "," && isNaN(vr.substring(i,i + 1)) != true ){
		 	s = s + vr.substring(i,i + 1);}
	}
	campo.value = s;
	return cp = campo.value
}


