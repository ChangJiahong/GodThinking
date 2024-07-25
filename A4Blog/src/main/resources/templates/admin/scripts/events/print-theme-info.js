/* global hexo */

'use strict'

hexo.on('ready', () => {
  if (!/^(g|s)/.test(hexo.env.cmd)) return
  const { version } = require('../../../../../../../../../../../Downloads/hexo-theme-A4-main/package.json')
  hexo.log.info(`

Welcome.
----------------------------------------------------------------
   ___  ____
  / _ |/ / /
 / __ /_  _/
/_/ |_|/_/                                 Reading and writing.
-----------------------------------------------------------------
Your hexo-theme-A4 version is ${version}. Maybe it can be updated.
The latest: https://github.com/HiNinoJay/hexo-theme-A4/releases
-----------------------------------------------------------------
`)
})