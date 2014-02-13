define append_if_no_such_line($file, $line, $refreshonly = 'false') {
   exec { "/bin/echo '$line' >> '$file'":
      unless      => "/bin/grep -Fxqe '$line' '$file'",
      path        => "/bin",
      refreshonly => $refreshonly,
   }
}

class must-have {
  include apt

exec { 'apt-get update':
    command => '/usr/bin/apt-get update',
  }

  package { ["vim",
              "bash",
              "wget",
              "unzip",
              "tmux",
	            "puppet-common",
              "mercurial",
	            "sshfs"]:
    ensure => present,
    require => Exec["apt-get update"],
  }

  file { "create-local-bin-folder":
    ensure => directory,
    path => "/home/vagrant/bin",
    owner => "vagrant",
    group => "vagrant",
    mode => '755',
    }

  exec { "download_play":
  user => "vagrant",
  group => "vagrant",
  path => "/usr/bin/:/bin/:/usr/local/bin/",
  cwd => "/home/vagrant/bin",
  command => "wget http://downloads.typesafe.com/play/2.2.2-RC2/play-2.2.2-RC2.zip && unzip play-2.2.2-RC2 && ln -s play-2.2.2-RC2/play ./ && chmod a+x play",
  creates => ["/home/vagrant/bin/play"],
  require => [File["create-local-bin-folder"]],
  }

}

include java7
include must-have

