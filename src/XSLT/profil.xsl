<?xml version="1.0" encoding="UTF-8"?>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:prf="http://myGame/tux"
                version="1.0">
    
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>profil.xsl</title>
                <link rel = "stylesheet"
                      type = "text/css"
                      href = "profil.css" />
            </head>
            <body>
                
                <p>Nom du profil :  <xsl:value-of select="//prf:nom/text()"/> </p>
                <p>avatar :  <xsl:value-of select="//prf:avatar/text()"/> </p>
                <p>Date de naissance :  <xsl:value-of select="//prf:anniversaire/text()"/> </p>


                <h2>Liste des parties</h2>
                <table>
                    <tr>
                        <td>Date</td>
                        <td>Temps</td>
                        <td>niveau</td>
                        <td>mot</td>
                        <td>% trouve</td>
                    </tr>
                    <!-- Ajout d'une ligne dans le tableau pour chaque partie -->
                    <xsl:apply-templates select="//prf:partie"/>
                       
                  
                </table>
            </body>
        </html>
    </xsl:template>
    
    <!-- On ajout chaque partie -->
    <xsl:template match="prf:partie">
        <tr>        
            <td>
                <xsl:value-of select="@date"/>
            </td>
            <td>
                <xsl:value-of select="prf:temps/text()"/>
            </td>
            <td>
                <xsl:value-of select="prf:mot/@niveau"/>
            </td>
            <td>
                <xsl:value-of select="prf:mot/text()"/>
            </td>
            <td>
                <xsl:value-of select="@trouvé"/>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
