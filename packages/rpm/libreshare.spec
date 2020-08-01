Name:            libreshare
Version:         0.0.1
Release:         1
Summary:         Web based sharing platform
License:         MIT
Source0:         %{name}-%{version}.tar.gz

#Requires:        bash
# BuildRequires:   systemd
BuildArch:       x86_64

%description
This package provides the full Libreshare application that offers
the web based sharing platform.

# Disable debuginfo because we don't ship binaries and thus don't need it
%define debug_package %{nil}

# Do not repack JAR files
%define __jar_repack %{nil}

# Prepare the package
%prep

# Unpack the source archive
%setup -q

%build

%install
echo "Installing all files"

# Install Libreshare
mkdir -p %{buildroot}/usr/local/bin
install -m 644 %{_builddir}/%{name}-%{version}/libreshare-web.jar %{buildroot}/usr/local/bin/libreshare-web.jar

# Install the config file
# TODO

%pre

echo "Setting up Libreshare"

# Create the service group
echo "Creating the group"
if ! grep -q "libreshare" /etc/passwd; then
    groupadd -r -g 5000 libreshare
fi

useradd -r libreshare -s /bin/false -u 5000 -g 5000

# Create the service user
echo "Creating the group"
if ! grep -q "libreshare" /etc/passwd; then
    groupadd -r -g 5000 libreshare
fi

%post

echo "Set up everything"

%preun

echo "Removing Libreshare"

%postun

echo "Removed Libreshare"


%files
/usr/local/bin/libreshare-web.jar

%changelog
