package com.workana.breakify.domain.highlight;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PeriodicElement {

    UNKNOWN("", 0),
    HYDROGEN("H", 1),
    HELIUM("He", 2),
    LITHIUM("Li", 3),
    BERYLLIUM("Be", 4),
    BORON("B", 5),
    CARBON("C", 6),
    NITROGEN("N", 7),
    OXYGEN("O", 8),
    FLUORINE("F", 9),
    NEON("Ne", 10),
    SODIUM("Na", 11),
    MAGNESIUM("Mg", 12),
    ALUMINIUM("Al", 13),
    SILICON("Si", 14),
    PHOSPHORUS("P", 15),
    SULFUR("S", 16),
    CHLORINE("Cl", 17),
    ARGON("Ar", 18),
    POTASSIUM("K", 19),
    CALCIUM("Ca", 20),
    SCANDIUM("Sc", 21),
    TITANIUM("Ti", 22),
    VANADIUM("V", 23),
    CHROMIUM("Cr", 24),
    MANGANESE("Mn", 25),
    IRON("Fe", 26),
    COBALT("Co", 27),
    NICKEL("Ni", 28),
    COPPER("Cu", 29),
    ZINC("Zn", 30),
    GALLIUM("Ga", 31),

    GERMANIUM("Ge", 32),
    ARSENIC("As", 33),
    SELENIUM("Se", 34),
    BROMINE("Br", 35),
    KRYPTON("Kr", 36),
    RUBIDIUM("Rb", 37),
    STRONTIUM("Sr", 38),
    YTTRIUM("Y", 39),
    ZIRCONIUM("Zr", 40),
    NIOBIUM("Nb", 41),
    MOLYBDENUM("Mo", 42),
    TECHNETIUM("Tc", 43),
    RUTHENIUM("Ru", 44),
    RHODIUM("Rh", 45),
    PALLADIUM("Pd", 46),
    SILVER("Ag", 47),
    CADMIUM("Cd", 48),
    INDIUM("In", 49),
    TIN("Sn", 50),

    ANTIMONY("Sb", 51),
    TELLURIUM("Te", 52),
    IODINE("I", 53),
    XENON("Xe", 54),
    CESIUM("Cs", 55),
    BARIUM("Ba", 56),
    LANTHANUM("La", 57),
    CERIUM("Ce", 58),
    PRASEODYMIUM("Pr", 59),
    NEODYMIUM("Nd", 60),
    PROMETHIUM("Pm", 61),
    SAMARIUM("Sm", 62),
    EUROPIUM("Eu", 63),
    GADOLINIUM("Gd", 64),
    TERBIUM("Tb", 65),
    DYSPROSIUM("Dy", 66),
    HOLMIUM("Ho", 67),
    ERBIUM("Er", 68),
    THULIUM("Tm", 69),
    YTTERBIUM("Yb", 70),

    LUTETIUM("Lu", 71),
    HAFNIUM("Hf", 72),
    TANTALUM("Ta", 73),
    TUNGSTEN("W", 74),
    RHENIUM("Re", 75),
    OSMIUM("Os", 76),
    IRIDIUM("Ir", 77),
    PLATINUM("Pt", 78),
    GOLD("Au", 79),
    MERCURY("Hg", 80),
    THALLIUM("Tl", 81),
    LEAD("Pb", 82),
    BISMUTH("Bi", 83),
    POLONIUM("Po", 84),
    ASTATINE("At", 85),
    RADON("Rn", 86),
    FRANCIUM("Fr", 87),
    RADIUM("Ra", 88),
    ACTINIUM("Ac", 89),
    THORIUM("Th", 90),

    PROTACTINIUM("Pa", 91),

    URANIUM("U", 92),

    NEPTUNIUM("Np", 93),

    PLUTONIUM("Pu", 94),

    AMERICIUM("Am", 95),

    CURIUM("Cm", 96),

    BERKELIUM("Bk", 97),

    CALIFORNIUM("Cf", 98),

    EINSTEINIUM("Es", 99),

    FERMIUM("Fm", 100),

    MENDELEVIUM("Md", 101),

    NOBELIUM("No", 102),

    LAWRENCIUM("Lr", 103),

    RUTHERFORDIUM("Rf", 104),

    DUBNIUM("Db", 105),

    SEABORGIUM("Sg", 106),

    BOHRIUM("Bh", 107),

    HASSIUM("Hs", 108),

    MEITNERIUM("Mt", 109),

    DARMSTADTIUM("Ds", 110),

    ROENTGENIUM("Rg", 111),

    COPERNICIUM("Cn", 112),

    NIHONIUM("Nh", 113),

    FLEROVIUM("Fl", 114),

    MOSCOVIUM("Mc", 115),

    LIVERMORIUM("Lv", 116),

    TENNESSINE("Ts", 117),

    OGANESSON("Og", 118);

    private final String symbol;

    private final int atomicNumber;

    PeriodicElement(String symbol, int atomicNumber) {
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
    }

    public static Map<String, PeriodicElement> symbolsAsMap() {
        return Arrays.stream(values())
                .collect(
                        Collectors.toMap(k -> k.symbol().toLowerCase(), value -> value)
                );
    }

    public static PeriodicElement forSymbol(final String periodicElementSymbol) {
        return Arrays.stream(values())
                .filter(pe -> pe.symbol.equalsIgnoreCase(periodicElementSymbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not fild periodic element for " + periodicElementSymbol));
    }

    public String symbol() {
        return symbol;
    }

    public int atomicNumber() {
        return atomicNumber;
    }

    public boolean isUnknown() {
        return this == UNKNOWN;
    }

    public int symbolLength() {
        return symbol.length();
    }
}
