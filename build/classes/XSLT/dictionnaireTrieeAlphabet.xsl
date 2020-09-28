<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:cdn="http://www.dictionnaire.fr"
                version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>dictionnaireTrieeAlphabet.xsl</title>
            </head>
            <body>
                <h1>Dictionnaire triee selon l'alphabet</h1>
                <table>
                    <tr>
                        <td>Mot</td>
                        <td>Niveau</td>
                    </tr>
                    
                    <xsl:apply-templates select="//cdn:mot">
                        <xsl:sort select="text()" order="ascending"/>
                    </xsl:apply-templates>
                </table>
            </body>
        </html>
    </xsl:template>
    
    
    <xsl:template match="cdn:mot">
        <tr>
            <td>        
                <xsl:value-of select="text()"/>
            </td>
            <td>        
                <xsl:value-of select="@niveau"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
