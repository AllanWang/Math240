ListOrig = [4149592715896318693849709,
            2534794902218749600993912,
            3190523465079491056026174,
            3074778338987907892876607,
            6695387053906005671850966,
            4175700148591818892491173,
            8826446380739620928032424,
            7637516797460242924439410,
            6122521835292773858096408,
            4663747783692557368786335,
            5460753392244685703003541,
            5287321411057070966032747,
            9908603268620169551480290,
            9053556600651377587680955,
            8035786579000472062282739,
            2800324771496092016260951,
            2861381897178387641667422,
            2295618289890599612462444,
            3609320268872430341040994,
            8911940383866081367599579,
            8812361425215055441593411,
            8510804340388781094218456,
            3571920468043639648135907,
            6552187093337802097595623,
            3872565547753784575895955,
            5008009114574162112380755,
            6944215747377606768170585,
            3559776541888469748844595,
            9444409489526917192610563,
            6817362279116187244859820,
            1505791602140569839099258,
            8320707535981754028228704,
            4939561658480825767385744,
            8874183362765776338088629,
            2266669455927754127373714,
            5543288059742315271232623,
            9157913960948294491121560,
            7173483501239335670241834,
            2366479376597920488162020,
            2644945616561317767103931,
            8469841629009117413770055,
            1665231495634042157546404,
            5454579198827007431833606,
            1496261788322202698694879,
            2771547512418388981431611,
            7598997421356279074235588,
            3067291834095505258427133,
            7891377811879799874199555,
            3773501627008738685687678,
            9135813709256569291123118,
            9301516311437350820005616,
            5416419980774293198192784,
            4703410049312089307674576,
            5947082012289884648775240,
            5873983441400621779060350,
            3566145369482468023013556,
            4521515441389125104681738,
            4203637421766819978132181,
            3432193396181029673628820,
            8012817379222283610791080,
            6278959181740309024069977,
            1910358927933141199499728,
            1195009937353983320868035,
            6513562820669022737281211,
            8486997562249070710469908,
            3016834820047227740241810,
            2211751985519734617943454,
            3545012129206758031685114,
            8044677024992678303677533,
            2584224731276816740922170,
            6290555404215550019767453,
            2857158598041802394177125,
            7345260806128369149195619,
            9372788288047132221461364,
            7594966622106745890391532,
            4989809903135247228357165,
            5443672808897147042450417,
            6408914424645781896682723,
            9880331330494589543692899,
            4698479497590845839991750,
            7301841474179606554154621,
            5419942579231882998716545,
            1940093812060882385434572,
            4465317039676819331607767,
            1651779750166676400663545,
            5143902608301281355973473,
            2179806408731136565797395,
            5198631961565789205768876,
            3900390251192442109710216,
            8528911472329547135176278]
List_10000 = [
    9709,
    3912,
    6174,
    6607,
    966,
    1173,
    2424,
    9410,
    6408,
    6335,
    3541,
    2747,
    290,
    955,
    2739,
    951,
    7422,
    2444,
    994,
    9579,
    3411,
    8456,
    5907,
    5623,
    5955,
    755,
    585,
    4595,
    563,
    9820,
    9258,
    8704,
    5744,
    8629,
    3714,
    2623,
    1560,
    1834,
    2020,
    3931,
    55,
    6404,
    3606,
    4879,
    1611,
    5588,
    7133,
    9555,
    7678,
    3118,
    5616,
    2784,
    4576,
    5240,
    350,
    3556,
    1738,
    2181,
    8820,
    1080,
    9977,
    9728,
    8035,
    1211,
    9908,
    1810,
    3454,
    5114,
    7533,
    2170,
    7453,
    7125,
    5619,
    1364,
    1532,
    7165,
    417,
    2723,
    2899,
    1750,
    4621,
    6545,
    4572,
    7767,
    3545,
    3473,
    7395,
    8876,
    216,
    6278
]

def mostCommonMod(List, mod):
    global Hash
    string = str(suma)+":"+str(index)
    if index <0 or suma<0:
        return 0
    elif string in Hash:
        return Hash[string]
    else:
        if List[index] == suma:
            toreturn =  1 + ispossiblesum(List,0,index-1) + ispossiblesum(List,suma,index-1)
        else:
            toreturn = ispossiblesum(List,suma-List[index],index-1) + ispossiblesum(List,suma,index-1)
        Hash[string] = toreturn
        return toreturn