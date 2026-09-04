package com.financalcbr.app.ui.theme

import androidx.compose.ui.graphics.Color

/* --------------------------------------------------------------------------- */
/* Paleta base obrigatoria (FinCalc_BR)                                         */
/* --------------------------------------------------------------------------- */

/** Azul escuro profundo — fundo no Dark, texto primario no Light. */
val DeepNavy = Color(0xFF0B1F3B)

/** Azul marinho clasico — superficies e cards no Dark. */
val Navy = Color(0xFF123A63)

/** Azul ardosia — primaria no Light; destaques e containers. */
val SlateBlue = Color(0xFF2F5D8C)

/** Azul claro acinzentado — superficies secundarias, bordas e contraste. */
val LightBlueGray = Color(0xFFC9D6E5)

/** Branco gelo — fundo principal no Light; texto claro no Dark. */
val IceWhite = Color(0xFFF2F5F8)

/* --------------------------------------------------------------------------- */
/* Cores de apoio (derivadas da paleta base)                                    */
/* --------------------------------------------------------------------------- */

/** Elevacao suave de superficie no Dark (um degrau acima de [Navy]). */
val NavyLift = Color(0xFF1B4A78)

/** Texto secundario/suporte no Light (variacao escura de [SlateBlue]). */
val SlateBlueDeep = Color(0xFF1C3B5E)

/** Contorno suave (Light) — variacao clara de [LightBlueGray]. */
val OutlineSoft = Color(0xFFB9C9DD)

/** Contorno em superficies escuras (Dark). */
val OutlineDark = Color(0xFF2A4A74)

/* --------------------------------------------------------------------------- */
/* Acentos financeiros (positivo/negativo) — com contraste WCAG em cada modo    */
/* --------------------------------------------------------------------------- */

/** Valor final / provento no Light. */
val FinancePositiveLight = Color(0xFF1B6B3A)

/** Valor final / provento no Dark (tonalidade mais clara para contraste). */
val FinancePositiveDark = Color(0xFF5ED994)

/** Deducao / negativo no Light. */
val FinanceNegativeLight = Color(0xFFB3261E)

/** Deducao / negativo no Dark. */
val FinanceNegativeDark = Color(0xFFF2B8B5)