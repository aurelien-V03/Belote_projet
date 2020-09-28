<?xml version="1.0" encoding="UTF-8"?>



<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:cdn="http://www.dictionnaire.fr"
                version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>dicoMotNiveauTriee.xsl</title>
            </head>
            <body>
                <h1>Mot regroupés par niveau et triés par alphabet</h1>
                <table>
                    <tr>
                        <td>Mot</td>
                        <td>Niveau</td>
                    </tr>
                    
                    <tr>
                        <td>NIVEAU 1</td>
                    </tr>
                    <xsl:apply-templates select="//cdn:mot[@niveau = 1]">
                        <xsl:sort select="text()" order="ascending"/>
                    </xsl:apply-templates>
                    <tr>
                        <td>NIVEAU 2</td>
                    </tr>
                    
                    <xsl:apply-templates select="//cdn:mot[@niveau = 2]">
                        <xsl:sort select="text()" order="ascending"/>
                    </xsl:apply-templates>
                    
                    <tr>
                        <td>NIVEAU 3</td>
                    </tr>
                    <xsl:apply-templates select="//cdn:mot[@niveau = 3]">
                        <xsl:sort select="text()" order="ascending"/>
                    </xsl:apply-templates>
                    
                    <tr>
                        <td>NIVEAU 4</td>
                    </tr>
                    <xsl:apply-templates select="//cdn:mot[@niveau = 4]">
                        <xsl:sort select="text()" order="ascending"/>
                    </xsl:apply-templates>
                    <tr>
                        <td>NIVEAU 5</td>
                    </tr>
                    
                    <xsl:apply-templates select="//cdn:mot[@niveau = 5]">
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
