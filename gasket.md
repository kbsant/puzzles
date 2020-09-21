# The Sierpinsky Gasket

Logo code:

    to gasket :side
      if :side < 10 [rt 30 fd :side rt 120 fd :side rt 120 fd :side rt 90 stop]
      gasket :side/2
      rt 30
      fd :side/2
      lt 30
      gasket :side/2
      rt 90
      fd :side/2
      rt 120
      fd :side/2
      rt 150
      gasket :side/2
      lt 90
      fd :side/2
      rt 90
    end
    clearscreen
    pd
    gasket 300

Try it in e.g. jslogo

